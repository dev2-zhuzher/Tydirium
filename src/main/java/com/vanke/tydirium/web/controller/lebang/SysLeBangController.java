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
 * @Description: 乐邦角色与本地角色关联管理
 *
 * @author: 郭时青
 * @date: 2017年8月23日 下午2:35:22
 */
@Controller
@RequestMapping(value = "/admin/sys/lebang")
public class SysLeBangController extends BaseController {

	/** 当前页数 */
	public static final String PAGE = "page";
	/** 当前页条数 */
	public static final String SIZE = "size";

	@Autowired
	private SysLeBangRoleService sysLeBangRoleService;

	@Autowired
	private SysRoleService sysRoleService;

	private static final String FINDLEBANGCODE = "findLeBangCode";

	/**
	 * 查询乐邦基本信息
	 * 
	 * @param model
	 * @param page
	 * @param size
	 * @param findLeBangCode
	 * @return
	 */
	@RequestMapping(value = "/info")
	public String leBangInfos(Model model, @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE) Integer page,
			@RequestParam(value = SIZE, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			@RequestParam(value = FINDLEBANGCODE, required = false) String findLeBangCode) {

		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		Page<SysLeBangRole> pager = sysLeBangRoleService.findAll(findLeBangCode, pageable);

		model.addAttribute("findLeBangCode", findLeBangCode);
		model.addAttribute("pager", pager);

		return "/sys/leBang/list";
	}

	/**
	 * 配置乐邦角色权限
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public String leBangInfo(Model model, @PathVariable Integer id) {

		SysLeBangRole leBangRole = sysLeBangRoleService.findOne(id);

		List<SysRole> sysRole = sysRoleService.findAll();

		model.addAttribute("leBangRole", leBangRole);

		model.addAttribute("role", sysRole);

		return "/sys/leBang/authority";
	}

	/**
	 * 更新或新增
	 * 
	 * @param model
	 * @param leBangRoleCode
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/save")
	public String save(Model model, @RequestParam("leBangRoleCode") String leBangRoleCode,
			@RequestParam("roleId") Long roleId) {
		SysLeBangRole sysLeBangRole = sysLeBangRoleService.findByLeBangRoleCode(leBangRoleCode);
		sysLeBangRole.setRoleId(roleId);
		sysLeBangRole.setUpdateTime(new Date());
		sysLeBangRoleService.save(sysLeBangRole);
		return super.REDIRECT + "/admin/sys/lebang/info";
	}

	/**
	 * 更新或新增
	 * 
	 * @param model
	 * @param leBangRole
	 * @param bindingResults
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model, @Valid SysLeBangRole leBangRole, BindingResult bindingResults) {
		long leBangRoleId = leBangRole.getId() == null ? 0 : leBangRole.getId();
		if (bindingResults.hasErrors()) {
			logger.info(bindingResults.getAllErrors());
			return super.REDIRECT + "/admin/sys/lebang/info/" + leBangRoleId;
		} else {
			SysLeBangRole sysLeBangRole = sysLeBangRoleService.findByLeBangRoleCode(leBangRole.getLeBangRoleCode());
			if (sysLeBangRole == null) {
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
