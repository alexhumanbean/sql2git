package com.sql2git;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Config.LoadConfig();
        new Starter().Start();
    }
}