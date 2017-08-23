package com.vanke.tydirium.web.controller.lebang;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vanke.tydirium.constants.CommonConstants;
import com.vanke.tydirium.entity.lebang.SysLeBangRole;
import com.vanke.tydirium.entity.sys.SysRole;
import com.vanke.tydirium.service.lebang.SysLeBangRoleService;
import com.vanke.tydirium.service.sys.SysRoleService;
import com.vanke.tydirium.web.controller.BaseController;

/**
 * 
 * 
 * @Description: 乐邦角色与本地角色关联管理
 *
 * @author: 郭时青
 * @date: 2017年8月23日 下午2:35:22
 */
@Controller
@RequestMapping(value = "/admin/sys/lebang")
public class SysLeBangController extends BaseController {

	public static final String FINDLEBANGCODE = "findLeBangCode";
	public static final String LEBANGROLE = "leBangRole";
	public static final String ROLE = "role";

	@Autowired
	private SysLeBangRoleService sysLeBangRoleService;

	@Autowired
	private SysRoleService sysRoleService;

	@RequestMapping(value = "/info")
	public String leBangInfos(Model model, @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE) Integer page,
			@RequestParam(value = SIZE, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			@RequestParam(value = FINDLEBANGCODE, required = false) String findLeBangCode) {
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		Page<SysLeBangRole> pager = sysLeBangRoleService.findAll(findLeBangCode, pageable);
		model.addAttribute(FINDLEBANGCODE, findLeBangCode);
		model.addAttribute(CommonConstants.PAGER, pager);
		return "/sys/leBang/list";
	}

	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public String leBangInfo(Model model, @PathVariable Integer id) {
		SysLeBangRole leBangRole = sysLeBangRoleService.findOne(id);
		List<SysRole> sysRole = sysRoleService.findAll();
		model.addAttribute(LEBANGROLE, leBangRole);
		model.addAttribute(ROLE, sysRole);
		return "/sys/leBang/authority";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model, @Valid SysLeBangRole leBangRole, BindingResult bindingResults) {
		long leBangRoleId = leBangRole.getId() == null ? 0 : leBangRole.getId();
		if (bindingResults.hasErrors()) {
			logger.info(bindingResults.getAllErrors());
			return super.REDIRECT + "/admin/sys/lebang/info/" + leBangRoleId;
		} else {
			SysLeBangRole sysLeBangRole = sysLeBangRoleService.findByLeBangRoleCode(leBangRole.getLeBangRoleCode());
			if(sysLeBangRole == null){
				sysLeBangRole = new SysLeBangRole();
			}
			sysLeBangRole.setLebangRoleCode(leBangRole.getLeBangRoleCode());
			sysLeBangRole.setRoleId(leBangRole.getRoleId());
			sysLeBangRole.setLeBangDescription(leBangRole.getLeBangDescription());
			sysLeBangRole.setUpdateTime(new Date());
			sysLeBangRoleService.save(sysLeBangRole);
		}
		return super.REDIRECT + "/admin/sys/lebang/info";
	}
}
