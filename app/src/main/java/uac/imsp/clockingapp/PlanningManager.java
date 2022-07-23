package uac.imsp.clockingapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class PlanningManager {
    private SQLiteDatabase Database = null;
    private PlanningSQLite planningSQLite;

    public PlanningManager(Context context) {
        planningSQLite = new PlanningSQLite(context);
    }

    public void open() {
        if (Database == null)
            Database = planningSQLite.getWritableDatabase();
    }

    public void close() {
        if (Database != null && Database.isOpen())
            Database.close();
    }

    public boolean delete(Planning planning) {
  String query="DELETE FROM planning WHERE id_planning=?";
        SQLiteStatement statement=Database.compileStatement(query);
        statement.bindLong(1,planning.getId());
        statement.executeUpdateDelete();
        return true;
    }


    public boolean create(Planning planning) {
      String query="INSERT INTO planning(heure_debut_officielle,heure_fin_officielle) VALUES (?,?)";

      SQLiteStatement statement=Database.compileStatement(query);
      statement.bindString(1,planning.getStartTime());
      statement.bindString(2,planning.getEndTime());
      statement.executeInsert();
    return false;
    }
    public boolean exist(Planning planning){
        String query="SELECT * FROM planning WHERE heure_debut_officielle=?" +
                " AND heure_fin_officielle=?";
        String [] selectArgs={
                planning.getStartTime(),planning.getEndTime()
        };
        Cursor cursor=Database.rawQuery(query,selectArgs);
        return cursor.getCount() != 0;
    }
}