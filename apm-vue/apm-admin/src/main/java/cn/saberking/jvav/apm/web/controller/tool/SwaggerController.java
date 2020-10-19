package cn.saberking.jvav.apm.web.controller.tool;

import cn.saberking.jvav.apm.common.core.controller.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * swagger 接口
 *
 * @author apm
 */
@Controller
@RequestMapping("/tool/swagger")
public class SwaggerController extends BaseController {
    @PreAuthorize("@ss.hasPermission('tool:swagger:view')")
    @GetMapping()
    public String index() {
        return redirect("/swagger-ui.html");
    }
}
