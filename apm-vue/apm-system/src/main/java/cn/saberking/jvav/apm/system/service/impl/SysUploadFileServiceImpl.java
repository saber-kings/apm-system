package cn.saberking.jvav.apm.system.service.impl;

import cn.saberking.jvav.apm.common.config.ApmConfig;
import cn.saberking.jvav.apm.common.core.domain.entity.SysUploadFile;
import cn.saberking.jvav.apm.common.enums.FileType;
import cn.saberking.jvav.apm.common.exception.file.FileSaveException;
import cn.saberking.jvav.apm.common.exception.file.InvalidExtensionException;
import cn.saberking.jvav.apm.common.utils.DateUtils;
import cn.saberking.jvav.apm.common.utils.SecurityUtils;
import cn.saberking.jvav.apm.common.utils.file.FileUploadUtils;
import cn.saberking.jvav.apm.common.utils.file.FileUtils;
import cn.saberking.jvav.apm.common.utils.file.MimeTypeUtils;
import cn.saberking.jvav.apm.system.mapper.SysUploadFileMapper;
import cn.saberking.jvav.apm.system.service.ISysUploadFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 文件Service业务层处理
 *
 * @author apm
 * @date 2020-10-16
 */
@Service
public class SysUploadFileServiceImpl implements ISysUploadFileService {
    @Resource
    private SysUploadFileMapper sysUploadFileMapper;

    /**
     * 查询文件
     *
     * @param id 文件ID
     * @return 文件
     */
    @Override
    public SysUploadFile selectSysUploadFileById(Long id) {
        return sysUploadFileMapper.selectSysUploadFileById(id);
    }

    /**
     * 查询文件列表
     *
     * @param sysUploadFile 文件
     * @return 文件
     */
    @Override
    public List<SysUploadFile> selectSysUploadFileList(SysUploadFile sysUploadFile) {
        return sysUploadFileMapper.selectSysUploadFileList(sysUploadFile);
    }

    /**
     * 新增文件
     *
     * @param sysUploadFile 文件
     * @return 结果
     */
    @Override
    public int insertSysUploadFile(SysUploadFile sysUploadFile) {
        sysUploadFile.setCreateTime(DateUtils.getNowDate());
        return sysUploadFileMapper.insertSysUploadFile(sysUploadFile);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveImage(MultipartFile image) throws IOException, InvalidExtensionException {
        if (!image.isEmpty()) {
            SysUploadFile uploadFile = FileUploadUtils.uploadFile(ApmConfig.getUploadPath(), image, MimeTypeUtils.IMAGE_EXTENSION, FileType.IMAGE.getCode());
            uploadFile.setType(FileType.IMAGE.getCode());
            //设置标题，默认与文件名一致
            uploadFile.setTitle(uploadFile.getName());
            //设置操作员
            uploadFile.setOperate(SecurityUtils.getUsername());
            //保存到数据库中
            int i = insertSysUploadFile(uploadFile);
            if (i == 0) {
                throw new FileSaveException("文件上传失败，请重试！");
            }
            return uploadFile.getUrl();
        }
        return null;
    }

    /**
     * 修改文件
     *
     * @param sysUploadFile 文件
     * @return 结果
     */
    @Override
    public int updateSysUploadFile(SysUploadFile sysUploadFile) {
        return sysUploadFileMapper.updateSysUploadFile(sysUploadFile);
    }

    /**
     * 批量删除文件
     *
     * @param ids 需要删除的文件ID
     * @return 结果
     */
    @Override
    public int deleteSysUploadFileByIds(Long[] ids) {
        return sysUploadFileMapper.deleteSysUploadFileByIds(ids);
    }

    /**
     * 删除文件信息
     *
     * @param id 文件ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSysUploadFileById(Long id) {
        //先获取文件信息
        SysUploadFile file = selectSysUploadFileById(id);
        //根据磁盘路径删除源文件
        FileUtils.deleteFile(file.getRealUrl());
        //最后删除数据库信息
        return sysUploadFileMapper.deleteSysUploadFileById(id);
    }
}
