package com.sql2git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Alex on 25.10.2015.
 */
public class Starter extends Common {
    public void Start() {
        logInfo(".....Starting sql2git.....");

        File gitWorkDir = new File(Config.CONF_REPOSITORY_PATH_LOCAL);
        try {
            Git git = Git.open(gitWorkDir);
            Repository repo = git.getRepository();

            ObjectId lastCommitId = repo.resolve(Constants.HEAD);

            RevWalk revWalk = new RevWalk(repo);
            RevCommit commit = revWalk.parseCommit(lastCommitId);

            RevTree tree = commit.getTree();

            TreeWalk treeWalk = new TreeWalk(repo);
            treeWalk.addTree(tree);
            treeWalk.setRecursive(true);
            treeWalk.setFilter(PathFilter.create("README.md"));
            if (!treeWalk.next())
            {
                System.out.println("Nothing found!");
                return;
            }

            ObjectId objectId = treeWalk.getObjectId(0);
            ObjectLoader loader = repo.open(objectId);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            loader.copyTo(out);
            System.out.println("README.md:\n" + out.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ошибка при работе с репозиторием:\n" + e.toString());
        }
    }
}
