package cn.saberking.jvav.apm.system.service;

import cn.saberking.jvav.apm.system.domain.SysLogininfor;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层
 *
 * @author apm
 */
public interface ISysLoginInforService {
    /**
     * 新增系统登录日志
     *
     * @param loginInfor 访问日志对象
     */
    void insertLoginInfor(SysLogininfor loginInfor);

    /**
     * 查询系统登录日志集合
     *
     * @param loginInfor 访问日志对象
     * @return 登录记录集合
     */
    List<SysLogininfor> selectLoginInforList(SysLogininfor loginInfor);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 删除的行数
     */
    int deleteLoginInforByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     */
    void cleanLoginInfor();
}
