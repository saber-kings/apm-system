package cn.saberking.jvav.apm.system.service;

import cn.saberking.jvav.apm.common.core.domain.entity.SysUploadFile;
import cn.saberking.jvav.apm.common.exception.file.InvalidExtensionException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 文件Service接口
 *
 * @author apm
 * @date 2020-10-16
 */
public interface ISysUploadFileService {
    /**
     * 查询文件
     *
     * @param id 文件ID
     * @return 文件
     */
    SysUploadFile selectSysUploadFileById(Long id);

    /**
     * 查询文件列表
     *
     * @param sysUploadFile 文件
     * @return 文件集合
     */
    List<SysUploadFile> selectSysUploadFileList(SysUploadFile sysUploadFile);

    /**
     * 新增文件
     *
     * @param sysUploadFile 文件
     * @return 结果
     */
    int insertSysUploadFile(SysUploadFile sysUploadFile);

    /**
     * 保存图片文件
     *
     * @param image 图片文件
     * @return 结果
     * @throws IOException IO异常
     * @throws InvalidExtensionException 文件扩展名异常
     */
    String saveImage(MultipartFile image) throws IOException, InvalidExtensionException;

    /**
     * 修改文件
     *
     * @param sysUploadFile 文件
     * @return 结果
     */
    int updateSysUploadFile(SysUploadFile sysUploadFile);

    /**
     * 批量删除文件
     *
     * @param ids 需要删除的文件ID
     * @return 结果
     */
    int deleteSysUploadFileByIds(Long[] ids);

    /**
     * 删除文件信息
     *
     * @param id 文件ID
     * @return 结果
     */
    int deleteSysUploadFileById(Long id);
}
