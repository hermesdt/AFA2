/*
 *  Copyright (C) 2011 Alvaro Duran Tovar
 *
 *  This file is part of AFA.
 *
 *  AFA is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package afa.view;

import afa.control.Constants;
import afa.control.plugins.PluginsManager;
import afa.model.Category;
import afa.model.MyMutableTreeNode;
import afa.model.Plugin;
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JTree;
import javax.swing.text.Position.Bias;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Alvaro Duran Tovar
 */
class Helper {



    static Vector<Category> treeToVector(JTree tree, boolean loadPluginContent) throws Exception {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        Enumeration categories = root.children();
        Vector<Category> vec = new Vector<Category>();



        while (categories.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) categories.nextElement();
            Category cat = new Category(node.toString());

            Enumeration children = node.children();


            while (children.hasMoreElements()) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
                Plugin p = null;


                if (loadPluginContent) {
                    p = PluginsManager.loadPlugin(Constants.getDirectorioPlugins()
                            + File.separator + node.toString() + File.separator + child.toString() + ".xml");


                } else {
                    p = new Plugin(child.toString());


                }
                cat.addPlugin(p);


            }
            vec.add(cat);


        }
        return vec;


    }

    static void cleanTree(JTree tree) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = new MyMutableTreeNode("/");
        model.setRoot(root);
    }


   static void expandAll(JTree tree, boolean expand) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();

        // Traverse tree from root
        expandAll(tree, new TreePath(root), expand);
    }

   static void expandAll(JTree tree, TreePath parent, boolean expand) {
        // Traverse children
        TreeNode node = (TreeNode) parent.getLastPathComponent();


        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }

        // Expansion or collapse must be done bottom-up
        if (expand) {
            tree.expandPath(parent);


        } else {
            tree.collapsePath(parent);


        }
    }
   
    /**
     * Locura! Este metodo tan largo se encarga de mover los elementos de un 
     * arbol a otro. No habra una forma mas facil de hacerlo? Revisar en un 
     * futuro.
     *
     * @param orig
     * @param dest
     */
    static void moveTreeToTree(JTree orig, JTree dest) {
        if (orig.getSelectionPath() == null) {
            return;
        }

        DefaultTreeModel model = (DefaultTreeModel) orig.getModel();
        DefaultMutableTreeNode root = ((DefaultMutableTreeNode) model.getRoot());

        TreePath path = orig.getSelectionPath();
        if (path.getPathCount() == 1) { //move all
            DefaultTreeModel model_dest = (DefaultTreeModel) dest.getModel();
            DefaultMutableTreeNode root_dest = ((DefaultMutableTreeNode) model_dest.getRoot());

            while (root.getChildCount() != 0) {
                JTree tree = new JTree();
                DefaultMutableTreeNode root_temp = new MyMutableTreeNode("/");
                root_temp.add((DefaultMutableTreeNode) root.getChildAt(0));
                ((DefaultTreeModel) tree.getModel()).setRoot(root_temp);
                tree.setSelectionRow(1);
                moveTreeToTree(tree, dest);
            }

            cleanTree(orig);
            model_dest.reload();
            expandAll(orig, true);
            expandAll(dest, true);
            return;
        }

        model.removeNodeFromParent((DefaultMutableTreeNode) path.getLastPathComponent());

        //remove second level on orig if its empty


        if (path.getPathCount() == 3) {
            int children_count = ((DefaultMutableTreeNode) path.getParentPath().
                    getLastPathComponent()).getChildCount();


            if (children_count == 0) {
                model.removeNodeFromParent((DefaultMutableTreeNode) path.getParentPath().
                        getLastPathComponent());
            }
        }

        model = (DefaultTreeModel) dest.getModel();
        root = ((DefaultMutableTreeNode) model.getRoot());
        //second Level
        String secondLevel = path.getPath()[1].toString();
        String thirdLevel = (path.getPathCount() == 3) ? path.getPath()[2].toString() : null;
        TreePath sec = dest.getNextMatch(secondLevel, 0, Bias.Forward);

        if (thirdLevel != null) {

            if (sec == null) {//second level exists on table jtable3
                DefaultMutableTreeNode s = new MyMutableTreeNode(secondLevel);
                root.add(s);
                s.add(new MyMutableTreeNode(thirdLevel));

                model.reload(root);


            } else {
                Enumeration e = root.children();


                while (e.hasMoreElements()) {
                    Object obj = e.nextElement();
                    String secLevel = obj.toString();


                    if (secLevel.equals(secondLevel)) {
                        ((DefaultMutableTreeNode) obj).add(new MyMutableTreeNode(thirdLevel));
                        model.reload((TreeNode) obj);
                        break;
                    }
                }
            }


        } else {
            //TreePath sec = dest.getNextMatch(secondLevel, 0, Bias.Forward);
            if (sec == null) {
                ((DefaultMutableTreeNode) model.getRoot()).add((DefaultMutableTreeNode) path.getLastPathComponent());
                model.reload(root);


            } else {
                Enumeration e = root.children();


                while (e.hasMoreElements()) {
                    Object obj = e.nextElement();
                    String secLevel = obj.toString();


                    if (secLevel.equals(secondLevel)) {
                        //get childs of orig tree
                        Enumeration c = ((DefaultMutableTreeNode) path.getLastPathComponent()).children();


                        while (c.hasMoreElements()) {
                            ((DefaultMutableTreeNode) obj).add(new MyMutableTreeNode(c.nextElement().toString()));


                        }
                        model.reload((TreeNode) obj);


                        break;


                    }
                }
            }
        }

        expandAll(orig, true);
        expandAll(dest, true);
    }

    static String[] treePathToStringArray(TreePath path) {
        String p = path.toString();
        p = p.replaceAll(", ", " ");
        p = p.replaceAll("\\[", " ");
        p = p.replaceAll("\\]", " ");
        p = p.trim();
        String[] s = p.split(" ");

        return s;
    }


    //load plugins tree on manage plugins tab
    static void loadPluginsOnTree(JTree tree, Vector<Category> vec) throws Exception {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        if (vec == null) {
            vec = PluginsManager.getLocalPlugins(false);
        }

        for (Category c : vec) {
            DefaultMutableTreeNode cat_node = new MyMutableTreeNode(c.getName());
            Vector<Plugin> plugins = c.getPlugins();

            for (Plugin p : plugins) {
                DefaultMutableTreeNode plug_node = new MyMutableTreeNode(p.getName());
                ((MyMutableTreeNode) plug_node).setPlugin(p);
                cat_node.add(plug_node);
            }
            root.add(cat_node);
        }
    }



    /**
     * Load the plugin specified with category and plugin name.
     * Should be called after treepathtostringarray
     * @param category - category of the plugin
     * @param plugin_name - name of the plugin
     * @return the plugin specified
     */
    static Plugin getPluginByStrings(String category, String plugin_name) throws Exception {
        return PluginsManager.loadPlugin(Constants.getDirectorioPlugins() + File.separator
                + category + File.separator + plugin_name + ".xml");
    }

}
