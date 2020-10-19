package cn.saberking.jvav.apm.system.mapper;

import cn.saberking.jvav.apm.common.core.domain.entity.SysUploadFile;

import java.util.List;

/**
 * 文件Mapper接口
 *
 * @author apm
 * @date 2020-10-16
 */
public interface SysUploadFileMapper {
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
     * 修改文件
     *
     * @param sysUploadFile 文件
     * @return 结果
     */
    int updateSysUploadFile(SysUploadFile sysUploadFile);

    /**
     * 删除文件
     *
     * @param id 文件ID
     * @return 结果
     */
    int deleteSysUploadFileById(Long id);

    /**
     * 批量删除文件
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSysUploadFileByIds(Long[] ids);
}
