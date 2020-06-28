package com.google.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * 1. дһ����̳�ϵͳ��ContentProvider
 * 
 * ����
 */
public class BankProvider extends ContentProvider {

    private SQLiteDatabase db;
	private static final int URI_INSERT = 0;
	private static final int URI_QUERY = 1;
	private static final int URI_ACCOUNT = 2;
	private static final int URI_BLACK = 3;
	
	//3. ����
	private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	//���URIƥ�����,URI:ָ�����Ӧ�õ�ĳ����Դ(���ݿ�,ͼƬ...)
	static{
		//URI�е� path:bao.ta.zhen.he.yao,����д�������淶
		matcher.addURI("tian.wang.gai.di.hu", "bao.ta.zhen.he.yao", URI_INSERT);
		
		//URI�е� path:query,���ַ������Ƽ�
		matcher.addURI("tian.wang.gai.di.hu", "query", URI_QUERY);
		
		//URI�е� path:account ���� ���Ƽ�ʹ��,Դ��Ҳ����ôд�ģ�
		matcher.addURI("tian.wang.gai.di.hu", "account", URI_ACCOUNT);
		
		//URI�е� path:blcak ���� ���Ƽ�ʹ�ã�
		matcher.addURI("tian.wang.gai.di.hu", "black", URI_BLACK);
	}
	/**
	 * ��ʼ�����ݿ�
	 */
	@Override
	public boolean onCreate() {
		BankDbOpenHelper helper = new BankDbOpenHelper(getContext());
		db = helper.getWritableDatabase();
		return false;
	}

	/**
	 * uri 	:content://tian.wang.gai.di.hu/insert(�̶�content��ͷ)
	 * values:Ҫ�����ݲ����ֵ
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int code = matcher.match(uri);
		switch(code) {
			case URI_INSERT: //z�˻���
			    long id = db.insert("account", null, values);//account:����
			    return Uri.parse("id="+id);
			case URI_BLACK://������
			    long id = db.insert("black", null, values);
			    return Uri.parse("id="+id);
			default:
			    throw new IllegalArgumentException("������ط��ɹ涨������Ȩ�����������ݿ⣡");
		}
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		int code = matcher.match(uri);
		switch(code) {
			case URI_QUERY:
			    Cursor cursor = db.query("account", projection, selection, selectionArgs, null, null, sortOrder);
			    return cursor;
			case URI_BLACK://������
			    Cursor cursor = db.query("account", projection, selection, selectionArgs, null, null, sortOrder);
			    return cursor;
			default:
			    throw new IllegalArgumentException("������ط��ɹ涨������Ȩ�����������ݿ⣡");
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int code = matcher.match(uri);
		if (code == URI_ACCOUNT) {
			int count = db.update("account", values, selection, selectionArgs);
			return count;
		}else if (code == URI_BLACK) {//������
			int count = db.update("account", values, selection, selectionArgs);
			return count;
		}else {
			throw new IllegalArgumentException("������ط��ɹ涨������Ȩ�����������ݿ⣡");
		}
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int code = matcher.match(uri);
		if (code == URI_ACCOUNT) {
			int count = db.delete("account", selection, selectionArgs);
			return count;
		}else if (code == URI_BLACK) {//������
			int count = db.delete("account", selection, selectionArgs);
			return count;
		}else {
			throw new IllegalArgumentException("������ط��ɹ涨������Ȩ�����������ݿ⣡");
		}
	}

	//���URIƥ�����  path:account/3 ɾ����3������
	matcher.addURI("tian.wang.gai.di.hu", "account/3", URI_ACCOUNT);
	//���URIƥ�����  path:account/# ɾ��ĳһ������,ʾ��#����id
	matcher.addURI("tian.wang.gai.di.hu", "account/#", URI_ACCOUNT_ID);
	/**
	 * ƥ�䵥��/��������,����д���鷳...
	 * vnd.android.cursor.item ������¼
     * vnd.android.cursor.dir/ ������¼
	 */
	@Override
	public String getType(Uri uri) {
		return null;
	}
}
