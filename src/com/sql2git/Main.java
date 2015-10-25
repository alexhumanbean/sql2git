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

import java.io.ByteArrayOutputStream;
import java.io.File;


public class Main
{
    public static void main(String[] args) throws Exception
    {
        Config.LoadConfig();
        File gitWorkDir = new File(Config.CONF_WORKING_DIR);

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
    }
}