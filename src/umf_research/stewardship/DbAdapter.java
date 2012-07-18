package umf_research.stewardship;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter {
	
	public static final String DATABASE_NAME = "stewardship.db";
	public static final int DATABASE_VERSION = 6;
	
	public static final String TABLE_locations = "locations";
	
	public static final String CREATE_TABLE_locations = "CREATE TABLE locations (_id INTEGER PRIMARY KEY,  name TEXT,  theme INTEGER,  lat FLOAT, lon FLOAT, textblock TEXT, picture INTEGER);";
	
	
	
	public static final String [] INSERT_DATA_locations = new String [] {
		"INSERT INTO LOCATIONS values (1, 'University of Maine at Farmington', 1, 44.400241, -70.085118, 'Established in 1864 as Maines first public institution of higher education, the University of Maine at Farmington (UMF) is Maines public liberal arts college, offering quality programs in teacher education, human services and arts and sciences.  With enrollment limited to just 2,000 students, UMF is about the same size as many of New Englands most selective private colleges and offers many of the same advantages, yet at a very attractive price point — providing a tremendous college education value in a spectacular natural setting. The University of Maine at Farmington is a founding member of The Council of Public Liberal Arts Colleges (COPLAC) an exclusive national group of 26 public colleges and universities committed to providing superior liberal arts and sciences education.'," + R.drawable.btn_01_72px + ");",
		"INSERT INTO LOCATIONS values (2, 'University of Maine at Farmington', 2, 44.400241, -70.085118, 'Water'," + R.drawable.btn_02_72px + ");",
		"INSERT INTO LOCATIONS values (3, 'University of Maine at Farmington', 3, 44.400241, -70.085118, 'Community'," + R.drawable.btn_03_72px + ");"
	}; 

	
	private OpenHelper DbHelper;
	private SQLiteDatabase Db;
	
	private final Context ADbAContext;
	
	
	DbAdapter(Context context) {
		this.ADbAContext = context;
	}
	
	
	private static class OpenHelper extends SQLiteOpenHelper {

		public OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			//Create Tables
			
			
			db.execSQL(CREATE_TABLE_locations);
			
			//Insert Data Into Tables
			multiStatement(INSERT_DATA_locations, db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS locations");
            onCreate(db);
		}
		
		public void multiStatement(String [] s, SQLiteDatabase MSdatabase) {
			for (int i = 0; i < s.length; i ++) {
				MSdatabase.execSQL(s[i]);
			}
		}
	}
	
    public DbAdapter open() throws SQLException {
        DbHelper = new OpenHelper(ADbAContext);
        Db = DbHelper.getWritableDatabase();
        return this;
	}
    
    public void close() {
    	DbHelper.close();
    }
    
    public Cursor getTitleTextAndTheme(int ID) {
    	String select = "SELECT locations.name, locations.textblock, locations.theme from locations where locations._id = " + ID;
    	Cursor cursor = Db.rawQuery(select,new String [] {});
    	return cursor;
    }
    
    public Cursor getTitleTextImageAndTheme(int ID) {
    	String select = "SELECT locations.name, locations.textblock, locations.picture, locations.theme from locations where locations._id = " + ID;
    	Cursor cursor = Db.rawQuery(select,new String [] {});
    	return cursor;
    }
    
    public Cursor getLatAndLonAndTheme() {
    	String select = "SELECT locations._id, locations.lat, locations.lon, locations.theme from locations";
    	Cursor cursor = Db.rawQuery(select,new String [] {});
    	return cursor;
    }
}
    