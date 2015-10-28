package com.sql2git.plsql;

import com.sql2git.Common;
import com.sql2git.Config;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.datatransfer.Clipboard;

/**
 * Created by Alex on 27.10.2015.
 */
public class PlSqlWindow extends Common {
    private static PlSqlWindow instance;
    private PlSqlWindow() {
        clipboardAccess = new ClipboardAccess();
    }
    public static PlSqlWindow getInstance() {
        if(instance == null) {
            instance = new PlSqlWindow();
        }
        return instance;
    }

    private ClipboardAccess clipboardAccess;
    private WinDef.HWND hwndTop;
    private WinDef.HWND hwndSql;

    public void FindWindow() {
        //final User32 lib = (User32) Native.loadLibrary("user32", User32.class);
        if(Config.CONST_TOP_CLASS_USE_FOREGROUND)
            hwndTop = User32Ex.INSTANCE.GetForegroundWindow();
        else
            hwndTop = User32Ex.INSTANCE.FindWindow(Config.CONST_TOP_CLASS_NAME, null);
        //System.out.println(hWnd_S);
        //5 - GW_CHILD (https://msdn.microsoft.com/en-us/library/windows/desktop/ms633515(v=vs.85).aspx)
        //HWND hWnd_sql_form = User32Ex.INSTANCE.GetWindow(hWnd_plsql_dev, new WinDef.DWORD(4));

        User32Ex.INSTANCE.EnumChildWindows(hwndTop, new User32.WNDENUMPROC() {
            @Override
            public boolean callback(WinDef.HWND hwnd, Pointer pntr) {
                char[] textBuffer = new char[512];
                char[] textBuffer2 = new char[512];
                User32Ex.INSTANCE.GetClassName(hwnd, textBuffer, 512);
                User32Ex.INSTANCE.GetWindowText(hwnd, textBuffer2, 512);
                String wClassName = Native.toString(textBuffer);
                String wWindowText = Native.toString(textBuffer2);
                //TTntPanel.UnicodeClass    //SQL Window - New
                //TSQLForm.UnicodeClass     //SQL Window - New
                //TTabSheet                 //SQL
                //System.out.println("className: " + wClassName + " title: " + wWindowText);
                if(Config.CONST_SQL_CLASS_NAME.equals(wClassName) && Config.CONST_SQL_WINDOW_TEXT.equals(wWindowText)){
                    hwndSql = hwnd;
                    logInfo("hwndSql " + hwndSql);
                    return false;
                }
                else {
                    return true;
                }
            }
        }, null);

        if(hwndTop == null) {
            logFatalError("PL/SQL Developer не запущен!");
        }
        if(hwndSql == null) {
            logFatalError("Не найдено SQL окно!");
        }
    }

    public void GetText() {
        if(hwndSql != null) {
            //User32Ex.INSTANCE.SendMessage(hwndSql, User32Ex.WM_SETTEXT, 0, Native.toByteArray("TextMessage"));

            /*byte[] windowText = new byte[512];
            int res = User32Ex.INSTANCE.SendMessage(hwndSql, User32Ex.WM_GETTEXTLENGTH, 0, 0);
            User32Ex.INSTANCE.SendMessage(hwndSql, User32Ex.WM_GETTEXT, res + 1 , windowText);

            String theText = Native.toString(windowText);
            System.out.println(theText);*/

            /*char[] windowText = new char[512];
            User32Ex.INSTANCE.GetWindowText(hwndSql, windowText, 512);
            String theText = Native.toString(windowText);
            System.out.println(theText);

            String newText = "Oops";
            User32Ex.INSTANCE.SetWindowText(hwndTop, newText.toCharArray());*/

            /*String theText = "";
            try {
                theText = clipboardAccess.getClipboardText();
                System.out.println(theText);
                clipboardAccess.setClipboardText("qweqweqwe");
            } catch (Exception e) {
                logFatalError("Ощибка при доступе к буферу обмена:\n" + e.toString());
            }*/

            String theText = "";
            theText = clipboardAccess.getSelectedText(hwndTop);
            System.out.println(theText);
        }
    }
}
