package com.sql2git;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        new Config().LoadConfig();
        new Starter().Start();
    }
}