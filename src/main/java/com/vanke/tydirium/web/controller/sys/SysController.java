package com.vanke.tydirium.web.controller.sys;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanke.tydirium.constants.CommonConstants;
import com.vanke.tydirium.entity.sys.SysModule;
import com.vanke.tydirium.entity.sys.SysResource;
import com.vanke.tydirium.entity.sys.SysRole;
import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.exception.WebException;
import com.vanke.tydirium.service.sys.SysModuleService;
import com.vanke.tydirium.service.sys.SysResourceService;
import com.vanke.tydirium.service.sys.SysRoleService;
import com.vanke.tydirium.service.sys.SysUserService;
import com.vanke.tydirium.web.controller.BaseController;

@Controller
@RequestMapping(value = "/admin/sys")
public class SysController extends BaseController {

	public static final String QUERYNICKNAME = "queryNickName";
	public static final String QUERYMOBILE = "queryMobile";
	public static final String ROLES = "roles";
	public static final String USER = "user";
	public static final String QUERYROLENAME = "queryRoleName";
	public static final String MODULES = "modules";
	public static final String ROLE = "role";
	public static final String RESOURCES = "resources";
	public static final String MODULE = "module";
	public static final String QUERYMODULENAME = "queryModuleName";
	public static final String RESOURCE = "resource";
	public static final String QUERYRESOURCESNAME = "queryResourcesName";
	public static final String MODULEIDS = "moduleIds";
	public static final String RESOURCEIDS = "resourceIds";

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysModuleService sysModuleService;
	@Autowired
	private SysResourceService sysResourceService;

	@RequestMapping(value = "/users", method = { RequestMethod.GET })
	public String user(Model model, @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE) Integer page,
			@RequestParam(value = SIZE, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			@RequestParam(value = QUERYNICKNAME, required = false) String queryNickName,
			@RequestParam(value = QUERYMOBILE, required = false) String queryMobile) throws WebException {
		try {
			Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
			Page<SysUser> pager = sysUserService.findAll(queryNickName, queryMobile, pageable);
			model.addAttribute(CommonConstants.PAGER, pager);
			model.addAttribute(QUERYNICKNAME, queryNickName);
			model.addAttribute(QUERYMOBILE, queryMobile);
			return "sys/user/list";
		} catch (Exception e) {
			throw new WebException(e, "10001", "用户列表加载错误");
		}
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public String user(Model model, @PathVariable Long userId) throws WebException {
		try {
			List<SysRole> roles = sysRoleService.findAll();
			SysUser user = sysUserService.findOne(userId);
			roles = sysRoleService.rolesCheck(user, roles);
			model.addAttribute(ROLES, roles);
			model.addAttribute(USER, user);
			return "/sys/user/add";
		} catch (Exception e) {
			throw new WebException(e, "", "用户详情错误");
		}
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String user(Model model, @Valid SysUser user, BindingResult bindingResults, Long[] roleIds)
			throws WebException {
		try {
			long userId = user.getId() == null ? 0 : user.getId();
			if (bindingResults.hasErrors()) {
				logger.info(bindingResults.getAllErrors());
				return super.REDIRECT + "/admin/sys/user/" + userId;
			}
			Set<SysRole> roles = sysRoleService.findByRoleIdIn(Arrays.<Long>asList(roleIds));
			if (roles == null || roles.size() < 1) {
				return super.REDIRECT + "/admin/sys/user/" + userId;
			}
			SysUser sysUser = sysUserService.findOne(userId);
			if (sysUser == null) {
				sysUser = new SysUser();
			}
			sysUser.setId(user.getId());
			sysUser.setNickName(user.getNickName());
			sysUser.setLoginName(user.getLoginName());
			sysUser.setFullName(user.getFullName());
			sysUser.setPassword(user.getPassword());
			sysUser.setMobile(user.getMobile());
			sysUser.setSex(user.getSex().getIndex());
			sysUser.setRoles(roles);
			sysUser.setDeleted(user.getDeleted());
			sysUser.setEmail(user.getEmail());
			if(StringUtils.isNotEmpty(user.getPassword())){
				sysUser.setPassword(user.getPassword());
			}
			sysUserService.save(sysUser);
			return super.REDIRECT + "/admin/sys/users";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public String roles(Model model, @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE) Integer page,
			@RequestParam(value = SIZE, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			@RequestParam(value = QUERYROLENAME, required = false) String queryRoleName) throws WebException {
		try {
			Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
			Page<SysRole> pager = sysRoleService.findAll(queryRoleName, pageable);
			model.addAttribute(QUERYROLENAME, queryRoleName);
			model.addAttribute(CommonConstants.PAGER, pager);
			return "/sys/role/list";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	@RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
	public String role(Model model, @PathVariable Long roleId) throws WebException {
		try {
			List<SysModule> modules = sysModuleService.findAll();
			SysRole role = sysRoleService.findOne(roleId);
			modules = sysModuleService.modulesCheck(role, modules);
			model.addAttribute(MODULES, modules);
			model.addAttribute(ROLE, role);
			return "/sys/role/add";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	@RequestMapping(value = "/role", method = { RequestMethod.POST })
	public String role(Model model, @Valid SysRole role, BindingResult bindingResults,
			@RequestParam(MODULEIDS) Long[] moduleIds) throws WebException {
		try {
			long roleId = role.getId() == null ? 0 : role.getId();
			if (bindingResults.hasErrors()) {
				logger.info(bindingResults.getAllErrors());
				return super.REDIRECT + "/admin/sys/role/" + roleId;
			}
			Set<SysModule> modules = sysModuleService.findByModuleIdIn(Arrays.<Long>asList(moduleIds));
			role.setModules(modules);
			role = sysRoleService.save(role);
			return super.REDIRECT + "/admin/sys/roles";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	@RequestMapping(value = "/modules", method = RequestMethod.GET)
	public String modules(Model model, @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE) Integer page,
			@RequestParam(value = SIZE, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			@RequestParam(value = QUERYMODULENAME, required = false) String queryModuleName) throws WebException {
		try {
			Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
			Page<SysModule> pager = sysModuleService.findAll(queryModuleName, pageable);
			model.addAttribute(QUERYMODULENAME, queryModuleName);
			model.addAttribute(CommonConstants.PAGER, pager);
			return "/sys/module/list";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	@RequestMapping(value = "/module/{moduleId}", method = RequestMethod.GET)
	public String module(Model model, @PathVariable Long moduleId) throws WebException {
		try {
			SysModule module = sysModuleService.findOne(moduleId);
			List<SysResource> resources = sysResourceService.findAll();
			resources = sysResourceService.resourcesCheck(module, resources);
			model.addAttribute(RESOURCES, resources);
			model.addAttribute(MODULE, module);
			return "/sys/module/add";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	@RequestMapping(value = "/module", method = { RequestMethod.POST })
	public String module(Model model, @Valid SysModule module, BindingResult bindingResults,
			@RequestParam(RESOURCEIDS) Long[] resourceIds) throws WebException {
		try {
			long moduleId = module.getId() == null ? 0 : module.getId();
			if (bindingResults.hasErrors()) {
				logger.info(bindingResults.getAllErrors());
				return super.REDIRECT + "/admin/sys/module/" + moduleId;
			}
			Set<SysResource> resources = sysResourceService.findByResourceIdIn(Arrays.<Long>asList(resourceIds));
			module.setResources(resources);
			module = sysModuleService.save(module);
			return super.REDIRECT + "/admin/sys/modules";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	// -- 资源操作

	/**
	 * 资源列表
	 *
	 * @param model
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public String resources(Model model, @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE) Integer page,
			@RequestParam(value = SIZE, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			@RequestParam(value = QUERYRESOURCESNAME, required = false) String queryResourcesName) throws WebException {
		try {
			Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
			Page<SysResource> pager = sysResourceService.findAll(queryResourcesName, pageable);
			model.addAttribute(QUERYRESOURCESNAME, queryResourcesName);
			model.addAttribute(CommonConstants.PAGER, pager);
			return "/sys/resource/list";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	/**
	 * 资源详情
	 *
	 * @param model
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value = "/resource/{resourceId}", method = RequestMethod.GET)
	public String resource(Model model, @PathVariable Long resourceId) throws WebException {
		try {
			SysResource resource = sysResourceService.findOne(resourceId);
			model.addAttribute(RESOURCE, resource);
			return "sys/resource/add";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	/**
	 * 资源添加或者更新
	 *
	 * @param model
	 * @param resource
	 * @param bindingResults
	 * @return
	 */
	@RequestMapping(value = "/resource", method = { RequestMethod.POST })
	public String resource(Model model, @Valid SysResource resource, BindingResult bindingResults) throws WebException {
		try {
			long resourceId = resource.getId() == null ? 0 : resource.getId();
			if (bindingResults.hasErrors()) {
				logger.info(bindingResults.getAllErrors());
				return super.REDIRECT + "/admin/sys/resource/" + resourceId;
			} else {
				resource = sysResourceService.save(resource);
			}
			return super.REDIRECT + "/admin/sys/resources";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	@RequestMapping(value = "/checkRoleName", method = { RequestMethod.GET })
	@ResponseBody
	public Boolean chenckRoleName(@RequestParam(value = "roleName") String roleName) throws WebException {
		try {
			SysRole role = sysRoleService.findByName(roleName);
			if (role == null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

}
