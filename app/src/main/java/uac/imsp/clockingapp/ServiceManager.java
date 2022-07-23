package uac.imsp.clockingapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

public class ServiceManager {

    private static SQLiteDatabase Database;
    private ServiceSQLite serviceSQLite;

    public ServiceManager(Context context) {
        serviceSQLite = new ServiceSQLite(context);
    }
    public ServiceManager  open(){

        if (Database==null)
            Database=serviceSQLite.getWritableDatabase();
        return this;
    }
    public void close(){
        if (Database!=null && Database.isOpen())
            Database.close();
    }



    public void create (Service service){

        SQLiteStatement statement;

        String query = "INSERT INTO service (nom) VALUES(?) ";

        statement=Database.compileStatement(query);
        statement.bindString(1,service.getName());

        statement.executeInsert();

    }

        public static void delete(Service service){
        String query = "DELETE FROM service WHERE id_service=?";
        SQLiteStatement statement=null ;

        statement = Database.compileStatement(query);
        statement.bindLong(1,service.getId());
        statement.executeUpdateDelete();

    }
    public void update(Service service,String name){
        String query = "UPDATE service SET nom=? WHERE id_service=?";
        SQLiteStatement statement;

        statement = Database.compileStatement(query);
        statement.bindString(1,service.getName());
        statement.bindLong(2,service.getId());
        statement.executeUpdateDelete();

    }
    public String[] getAllServices(){
        ArrayList <String> service= new ArrayList<>();

        String query="SELECT nom FROM service";
        Cursor cursor=Database.rawQuery(query,null);
        //cursor.moveToFirst();
        while (cursor.moveToNext())
            service.add(cursor.getString(0));
        Log.d("myTag",service.toArray(new String[service.size()]).toString());
        return service.toArray(new String[service.size()]);


    }


    public int  searchService(String name){
        String query="SELECT id_service FROM service WHERE nom=? ";
        String selectArg[]={name};
        Cursor cursor=Database.rawQuery(query,selectArg);
        cursor.moveToFirst();
        return Integer.parseInt(cursor.getString(0));
    }


}
