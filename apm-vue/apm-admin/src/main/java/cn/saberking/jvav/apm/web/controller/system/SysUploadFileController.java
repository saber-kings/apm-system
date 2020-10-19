package cn.saberking.jvav.apm.web.controller.system;

import cn.saberking.jvav.apm.common.annotation.Log;
import cn.saberking.jvav.apm.common.core.controller.BaseController;
import cn.saberking.jvav.apm.common.core.domain.AjaxResult;
import cn.saberking.jvav.apm.common.core.domain.entity.SysUploadFile;
import cn.saberking.jvav.apm.common.core.page.TableDataInfo;
import cn.saberking.jvav.apm.common.enums.BusinessType;
import cn.saberking.jvav.apm.common.utils.poi.ExcelUtil;
import cn.saberking.jvav.apm.system.service.ISysUploadFileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文件Controller
 *
 * @author apm
 * @date 2020-10-16
 */
@RestController
@RequestMapping("/system/file")
public class SysUploadFileController extends BaseController {
    @Resource
    private ISysUploadFileService sysUploadFileService;

    /**
     * 查询文件列表
     */
    @PreAuthorize("@ss.hasPermission('system:file:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUploadFile sysUploadFile) {
        startPage();
        List<SysUploadFile> list = sysUploadFileService.selectSysUploadFileList(sysUploadFile);
        return getDataTable(list);
    }

    /**
     * 导出文件列表
     */
    @PreAuthorize("@ss.hasPermission('system:file:export')")
    @Log(title = "文件", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysUploadFile sysUploadFile) {
        List<SysUploadFile> list = sysUploadFileService.selectSysUploadFileList(sysUploadFile);
        ExcelUtil<SysUploadFile> util = new ExcelUtil<>(SysUploadFile.class);
        return util.exportExcel(list, "file");
    }

    /**
     * 获取文件详细信息
     */
    @PreAuthorize("@ss.hasPermission('system:file:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysUploadFileService.selectSysUploadFileById(id));
    }

    /**
     * 新增文件
     */
    @PreAuthorize("@ss.hasPermission('system:file:add')")
    @Log(title = "文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUploadFile sysUploadFile) {
        return toAjax(sysUploadFileService.insertSysUploadFile(sysUploadFile));
    }

    /**
     * 修改文件
     */
    @PreAuthorize("@ss.hasPermission('system:file:edit')")
    @Log(title = "文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUploadFile sysUploadFile) {
        return toAjax(sysUploadFileService.updateSysUploadFile(sysUploadFile));
    }

    /**
     * 删除文件
     */
    @PreAuthorize("@ss.hasPermission('system:file:remove')")
    @Log(title = "文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysUploadFileService.deleteSysUploadFileByIds(ids));
    }
}
