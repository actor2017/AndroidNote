DecimalFormat ,DecimalFormat��NumberFormatһ�����������,��Ҫ�Ǹ�ʽ��ʮ������

private String formatToString(T item) {
    if (item instanceof Float || item instanceof Double) {
        //DecimalFormat.getInstance().format();
        return new DecimalFormat("0.00").format(item);//java.text.DecimalFormat
    }
    return item.toString();
}