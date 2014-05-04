package com.olimposystems.grandespensadores.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.olimposystems.grandespensadores.type.GrandesPensadores;

public class DataBaseHelper extends SQLiteOpenHelper {

	private final File dbPath;

	private static String nomeDoBanco = "gp_" + GrandesPensadores.database.value();

	private SQLiteDatabase meuBancoDeDados;

	private final Context meuContext;

	/**
	 * Constructor
	 * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DataBaseHelper(Context context) {

		super(context,nomeDoBanco,null,1);
		// dbPath = context.getFilesDir().getPath() + "/data/com/olimposystems/grandespensadores/databases/";
		dbPath = context.getDatabasePath(nomeDoBanco);
		meuContext = context;
		createDataBase();
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own database.
	 */
	public void createDataBase() {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
			try {
				copyDataBase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			// By calling this method and empty database will be created into the default system path
			// of your application so we are gonna be able to overwrite that database with our database.
			getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				Log.e(GrandesPensadores.categoriaLog.value(),"Error copying database",e);

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = dbPath.toString();
			checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			Log.e(GrandesPensadores.categoriaLog.value(),"Error checking database",e);

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created empty database in the
	 * system folder, from where it can be accessed and handled.
	 * This is done by transfering bytestream.
	 */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = meuContext.getAssets().open(nomeDoBanco);

		// Path to the just created empty db
		String outFileName = dbPath.toString();

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ( (length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer,0,length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = dbPath.toString();
		meuBancoDeDados = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if (meuBancoDeDados != null) {
			meuBancoDeDados.close();
		}

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {

	}

	// Add your public helper methods to access and get content from the database.
	// You could return cursors by doing "return meuBancoDeDados.query(....)" so it'd be easy
	// to you to create adapters for your views.

	public SQLiteDatabase getBancoDeDados() {

		return meuBancoDeDados = meuContext.openOrCreateDatabase(nomeDoBanco,Context.MODE_PRIVATE,null);

	}

	public long contaLinhasDeTabela(String tabela) {

		meuBancoDeDados = meuContext.openOrCreateDatabase(nomeDoBanco,Context.MODE_PRIVATE,null);
		Cursor mCount = meuBancoDeDados.rawQuery("select count(*) from " + tabela,null);
		mCount.moveToFirst();
		long count = mCount.getLong(0);
		mCount.close();

		return count;

	}

}
