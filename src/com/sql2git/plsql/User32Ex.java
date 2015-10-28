package com.sql2git.plsql;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.win32.W32APIOptions;

/**
 * Created by Alex on 28.10.2015.
 */
public interface User32Ex extends User32 {
    public User32Ex INSTANCE = (User32Ex) Native.loadLibrary("user32", User32Ex.class, W32APIOptions.DEFAULT_OPTIONS);

    public final int WM_SETTEXT = 0x000C;
    public final int WM_GETTEXT = 0x00D;
    public final int WM_GETTEXTLENGTH = 0x00A;

    int SendMessage(HWND hWnd, int msg, WPARAM wParam, int lParam);
    <T> int SendMessage(HWND hWnd, int msg, WPARAM wParam, T lParam);
    int SendMessage(HWND hWnd, int msg, int wParam, char[] lParam);
    <T> int SendMessage(HWND hWnd, int msg, int wParam, T lParam);
}

