package com.vanke.tydirium.web.controller.lebang;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.vanke.tydirium.entity.lebang.SysLeBangRole;
import com.vanke.tydirium.entity.sys.SysRole;
import com.vanke.tydirium.service.lebang.SysLeBangRoleService;
import com.vanke.tydirium.service.sys.SysRoleService;
import com.vanke.tydirium.web.controller.BaseController;

@Controller
@RequestMapping(value = "/admin/sys/lebang")
public class SysLeBangController extends BaseController{
	
	/** 当前页数 */
	public static final String PAGE = "page";
	/** 当前页条数 */
	public static final String SIZE = "size";
	
	@Autowired
	private SysLeBangRoleService sysLeBangRoleService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	private static final String FINDLEBANGCODE = "findLeBangCode";
	
	@RequestMapping(value = "/info")
	public String leBangInfos(Model model, @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE) Integer page,
			@RequestParam(value = SIZE, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			@RequestParam(value = FINDLEBANGCODE, required = false) String findLeBangCode){
		
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		Page<SysLeBangRole> pager = sysLeBangRoleService.findAll(findLeBangCode, pageable);
		
		model.addAttribute("findLeBangCode", findLeBangCode);
		model.addAttribute("pager", pager);
		
		return "/sys/leBang/list";
	}
	
	@RequestMapping(value = "/info/{id}",method = RequestMethod.GET)
	public String leBangInfo(Model model, @PathVariable Integer id){
		
		SysLeBangRole leBangRole = sysLeBangRoleService.findOne(id);
		
		List<SysRole> sysRole = sysRoleService.findAll();
		
		model.addAttribute("leBangRole", leBangRole);
		
		model.addAttribute("role", sysRole);
		
		return "/sys/leBang/authority";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(Model model,@RequestParam("leBangRoleCode") String leBangRoleCode,@RequestParam("roleId") Long roleId){
		SysLeBangRole sysLeBangRole = sysLeBangRoleService.findByLeBangRoleCode(leBangRoleCode);
		sysLeBangRole.setRoleId(roleId);
		sysLeBangRole.setUpdateTime(new Date());
		sysLeBangRoleService.save(sysLeBangRole);
		return super.REDIRECT+"/admin/sys/lebang/info";
	}
}
