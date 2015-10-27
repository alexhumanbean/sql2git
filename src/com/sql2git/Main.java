package com.sql2git;

import com.sql2git.plsql.PlSqlWindow;
import com.sql2git.repository.RepositoryApi;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Config.getInstance().LoadConfig();
        PlSqlWindow.getInstance().FindWindow();
        RepositoryApi.getInstance().Init();
    }
}