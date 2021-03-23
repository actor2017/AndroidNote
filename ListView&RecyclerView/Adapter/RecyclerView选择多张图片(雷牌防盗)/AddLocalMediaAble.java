package com.ysytech.zhongjiao.adapter;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;
import java.util.Map;

/**
 * description: 添加文件公共方法抽取
 * @see AddPicAdapter
 * @see AddVideoAdapter
 * @see AddAudioAdapter
 *
 * 选择图片示例使用:
 * //是否已经选择了图片
 * boolean picSelected = picAdapter.hasFileSelected();
 *
 * //上传图片
 * private void uploadPics() {
 *     //获取已选中的文件
 *     List<LocalMedia> selectFiles = picAdapter.getSelectFiles();
 *     for (LocalMedia selectFile : selectFiles) {
 *         //如果这个文件还未上传到服务器
 *         if (!picAdapter.hasUpload(selectFile.getPath())) {
 *             //上传文件, 并设置上传结果, 例: http://www.xxx.com/1.jpg
 *             picAdapter.setUpload(selectFile.getPath(), "http://www.xxx.com/1.jpg");
 *         }
 *     }
 *     //获取已上传文件Map<文件路径, 文件上传路径>, 注意: 如果上传了1次后又选择了新的文件, 那么上传路径有可能=null
 *     Map<String, String> alreadyUploads = picAdapter.getAlreadyUploadFiles();
 * }
 *
 * @author : 李大发
 * date       : 2021/2/8 on 11
 * @version 1.0
 */
public interface AddLocalMediaAble <T> {

    /**
     * 最后一个文件占位, path例: content://media/external/file/119729
     */
    public static final LocalMedia EXTRA_LAST_MEDIA = new LocalMedia("path", 0, 0, "image/jpeg");

    /**
     * 记录已选图片
     * @deprecated 用户不需要调用这个方法
     */
    @Deprecated
    public abstract List<LocalMedia> getLocalMedias();

    /**
     * Map<文件本地path, 文件已上传路径>
     * @return 返回一个全局变量Map, 不要直接new对象返回
     * @deprecated 用户不需要调用这个方法
     */
    @Deprecated
    public abstract Map<String, T> getUploads();

    /**
     * 返回已上传服务器的文件
     * @return 返回一个全局变量Map, 不要直接new对象返回
     * @deprecated 用户不需要调用这个方法
     */
    @Deprecated
    public abstract Map<String, T> getAlreadyUploads();



    default void initAddLocalMediaAble() {
        getLocalMedias().clear();
        getUploads().clear();
        getAlreadyUploads().clear();
    }

    /**
     * 是否有图片选择
     */
    default boolean hasFileSelected() {
        return !getLocalMedias().isEmpty();
    }

    /**
     * @return 获取已选择的文件
     */
    default List<LocalMedia> getSelectFiles() {
        return getLocalMedias();
    }

    /**
     * 文件是否已上传服务器
     * @param path 文件路径
     * @return 返回是否已上传
     */
    default boolean hasUpload(String path) {
        return path != null && getUploads().get(path) != null;
    }

    /**
     * 设置文件上传路径
     * @param path 文件
     * @param uploadPath 上传路径
     */
    default void setUpload(String path, T uploadPath) {
        getUploads().put(path, uploadPath);
    }

    /**
     * @return 获取已上传结果Map, 如果上传文件后又选择了图片, 那么已上传路径可能为空.
     */
    default Map<String, T> getAlreadyUploadFiles() {
        Map<String, T> alreadyUploads = getAlreadyUploads();
        alreadyUploads.clear();
        for (LocalMedia localMedia : getLocalMedias()) {
            String path = localMedia.getPath();
            alreadyUploads.put(path, getUploads().get(path));
        }
        return alreadyUploads;
    }
}
