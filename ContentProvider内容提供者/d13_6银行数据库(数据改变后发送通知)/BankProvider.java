package com.itheima.bank;

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
 * ˫����
 * //��֪ͨ���������ݱ仯��,ֻ����1�д���ı�
 * getContext().getContentResolver().notifyChange(uri, null);//null:֪ͨ���й۲���
 */
public class BankProvider extends ContentProvider {

	private static final int	URI_SUCC	= 0;
	private static final int	URI_SUCC_QUERY	= 1;
	private static final int	URI_SUCC_ACCOUNT	= 2;
	private static final int	URI_SUCC_BLACK	= 3;
	
	//3. ����
	static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	private SQLiteDatabase	db;
	static{
		//��� ƥ�����
		matcher.addURI("tian.wang.gai.di.hu", "bao.ta.zhen.he.yao", URI_SUCC);
		//���URIƥ�����  path:query������
		matcher.addURI("tian.wang.gai.di.hu", "query", URI_SUCC_QUERY);
		
		//���URIƥ�����  path:account ���� ���Ƽ�ʹ�ã�
		matcher.addURI("tian.wang.gai.di.hu", "account", URI_SUCC_ACCOUNT);
		//���URIƥ�����  path:blcak ���� ���Ƽ�ʹ�ã�
		matcher.addURI("tian.wang.gai.di.hu", "black", URI_SUCC_BLACK);
		
		//���URIƥ�����  path:account/3 ���� ���Ƽ�ʹ�ã�
		matcher.addURI("tian.wang.gai.di.hu", "account/3", URI_SUCC_ACCOUNT);
		//���URIƥ�����  path:account/# ���� ���Ƽ�ʹ�ã�
		matcher.addURI("tian.wang.gai.di.hu", "account/#", URI_SUCC_ACCOUNT);
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

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		int code = matcher.match(uri);
		if (code == URI_SUCC_QUERY) {
			Cursor cursor = db.query("account", projection, selection, selectionArgs, null, null, sortOrder);
			//��֪ͨ���������ݱ仯��
			getContext().getContentResolver().notifyChange(uri, null);
			return cursor;
		}else if (code == URI_SUCC_BLACK) {//������
			Cursor cursor = db.query("account", projection, selection, selectionArgs, null, null, sortOrder);
			return cursor;
		}else {
			throw new IllegalArgumentException("������ط��ɹ涨������Ȩ�����������ݿ⣡");
		}
	}

	/**
	 * 4. ʵ����ɾ�Ĳ鷽��
	 * uri 		:    content://tian.wang.gai.di.hu/bao.ta.zhen.he.yao
	 * values	:	 Ҫ�����ݲ����ֵ
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int code = matcher.match(uri);
		if(code == URI_SUCC){//�˻���
			long id = db.insert("account", null, values);
			
			//��֪ͨ���������ݱ仯��
			getContext().getContentResolver().notifyChange(uri, null);
			
			return Uri.parse("id="+id);
		}else if (code == URI_SUCC_BLACK) {//������
			long id = db.insert("black", null, values);
			return Uri.parse("id="+id);
		}else {
			throw new IllegalArgumentException("������ط��ɹ涨������Ȩ�����������ݿ⣡");
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int code = matcher.match(uri);
		if (code == URI_SUCC_ACCOUNT) {
			int id = db.delete("account", selection, selectionArgs);
			//��֪ͨ���������ݱ仯��
			getContext().getContentResolver().notifyChange(uri, null);
			return id;
		}else if (code == URI_SUCC_BLACK) {//������
			int id = db.delete("account", selection, selectionArgs);
			return id;
		}else {
			throw new IllegalArgumentException("������ط��ɹ涨������Ȩ�����������ݿ⣡");
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int code = matcher.match(uri);
		if (code == URI_SUCC_ACCOUNT) {
			int id = db.update("account", values, selection, selectionArgs);
			//��֪ͨ���������ݱ仯��
			getContext().getContentResolver().notifyChange(uri, null);
			return id;
		}else if (code == URI_SUCC_BLACK) {//������
			int id = db.update("account", values, selection, selectionArgs);
			return id;
		}else {
			throw new IllegalArgumentException("������ط��ɹ涨������Ȩ�����������ݿ⣡");
		}
	}

	/**
	 * vnd.android.cursor.item ������¼
		vnd.android.cursor.dir/ ������¼
	 */
	@Override
	public String getType(Uri uri) {
		return null;
	}
}
