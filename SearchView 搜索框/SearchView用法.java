
        // ���������ı�����
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // �����"������ť"ʱ�����÷���
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // ������"���ݸı�"ʱ�����÷���
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    mListView.setFilterText(newText);
                }else{
                    mListView.clearTextFilter();
                }
                return false;
            }
        });

/**======================================================================
JAVA�г��õķ���================================*/

//����������Ĭ���Ƿ��Զ���СΪͼ�ꡣ
setIconifiedByDefault(boolean iconified)
// Ϊ���������ü�����
setOnQueryTextListener(SearchView��OnQueryTextListener listener)
 // �����Ƿ���ʾ������ť
setSubmitButtonEnabled(boolean enabled)
// �����������ڵ�Ĭ����ʾ����ʾ�ı�
setQueryHint(CharSequence hint)
onQueryTextSubmit�ǵ���������Listener ����true,���̲���ʧ������false��������ʧ��
onQueryTextChange�����������ݱ仯��Listener

// �ֶ�չ��SearchView�����
onActionViewExpanded

.setOnCloseListener();//���ùرռ���

/**================================================================================
		ʾ�� - �޸�SearchView��ʽ
����
�鿴SearchView�Ĳ����ļ��������ȥ��R�ļ����޷��鿴�������ݡ�
�������
��AS��ȫ���������������ò��ּ��ɲ鿴�ò������ݡ�===================================*/

//���� ���� �޸�Searchview�����С��ɫ��ͬʱ��Ҫ���²���ʹText����

//����id-search_src_text��ȡTextView
SearchView.SearchAutoComplete searchText = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
//�޸������С
searchText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
//���²��֣�ʹ�����
LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
lp.gravity = Gravity.CENTER_VERTICAL;
searchText.setLayoutParams(lp);
//�޸�������ɫ
searchText.setTextColor(ContextCompat.getColor(this, R.color.common_text_gray));
searchText.setHintTextColor(ContextCompat.getColor(this, R.color.common_text_gray));


// ����ͨ��xml����app:searchIcon���޸�ͼ�����ʽ�ᵼ��drawable����(��ʾ������)��
// ���о�����ΪSearchView��ImageVIew������padding���µĽ��������ֻ��ͨ����̬�������޸ġ�

//���� ���� �޸�SearchView���ͼ��

//����id-search_mag_icon��ȡImageView
ImageView searchButton = (ImageView) searchView.findViewById(R.id.search_mag_icon);
//��������ImageView�Ŀ�ߣ�ʹ��Ϊ����ӦͼƬ���
LinearLayout.LayoutParams lpimg = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
lpimg.gravity = Gravity.CENTER_VERTICAL;
searchButton.setLayoutParams(lpimg);
searchButton.setImageResource(R.drawable.goods_search_icon);

//�������ڲ���ImageView�Ŀ�ߴ�С
// searchButton.setBackgroundColor(Color.BLUE);