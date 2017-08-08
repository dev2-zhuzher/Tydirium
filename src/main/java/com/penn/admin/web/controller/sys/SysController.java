package com.penn.admin.web.controller.sys;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.penn.admin.entity.sys.SysModule;
import com.penn.admin.entity.sys.SysResource;
import com.penn.admin.entity.sys.SysRole;
import com.penn.admin.entity.sys.SysUser;
import com.penn.admin.exception.WebException;
import com.penn.admin.service.sys.SysModuleService;
import com.penn.admin.service.sys.SysResourceService;
import com.penn.admin.service.sys.SysRoleService;
import com.penn.admin.service.sys.SysUserService;
import com.penn.admin.web.controller.BaseController;

@Controller
@RequestMapping(value = "/admin/sys")
public class SysController extends BaseController {

	public static final String PAGER = "pager";
	public static final String QUERYMOBILE = "queryMobile";
	public static final String ROLES = "roles";
	public static final String USER = "user";
	public static final String QUERYROLENAME = "queryRoleName";
	public static final String MODULES = "modules";
	public static final String ROLE = "role";
	public static final String RESOURCES = "resources";
	public static final String MODULE = "module";
	public static final String RESOURCE = "resource";

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysModuleService sysModuleService;
	@Autowired
	private SysResourceService sysResourceService;

	@RequestMapping(value = "/users", method = { RequestMethod.GET })
	public String user(Model model, @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
			@RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer size) throws WebException {
		try {
			Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
			Page<SysUser> pager = sysUserService.findAll(pageable);
			model.addAttribute(PAGER, pager);
			return "sysUsers";
		} catch (Exception e) {
			throw new WebException(e, "10001", "用户列表加载错误");
		}
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public String user(Model model, @PathVariable Long userId) throws WebException {
		try {
			List<SysRole> roles = sysRoleService.findAll();
			SysUser user = sysUserService.findOne(userId);
			model.addAttribute(ROLES, roles);
			model.addAttribute(USER, user);
			return "sysUser";
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
			sysUser.setNickName(user.getNickName());
			sysUser.setFullName(user.getFullName());
			sysUser.setPassword(user.getPassword());
			sysUser.setMobile(user.getMobile());
			sysUser.setRoles(roles);
			sysUser.setDeleted(user.getDeleted());
			return super.REDIRECT + "/admin/sys/users";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public String roles(Model model, @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
			@RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			@RequestParam(value = "queryRoleName", required = false) String queryRoleName) throws WebException {
		try {
			Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "roleId"));
			Page<SysRole> pager = sysRoleService.findAll(pageable);
			model.addAttribute(QUERYROLENAME, queryRoleName);
			model.addAttribute("pager", pager);
			return "sysRoles";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	@RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
	public String role(Model model, @PathVariable Long roleId) throws WebException {
		try {
			List<SysModule> modules = sysModuleService.findAll();
			SysRole role = sysRoleService.findOne(roleId);
			model.addAttribute(MODULES, modules);
			model.addAttribute(ROLE, role);
			return "sysRole";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	@RequestMapping(value = "/role", method = { RequestMethod.POST })
	public String role(Model model, @Valid SysRole role, BindingResult bindingResults,
			@RequestParam("moduleIds") Long[] moduleIds) throws WebException {
		try {
			long roleId = role.getId() == null ? 0 : role.getId();
			if (bindingResults.hasErrors()) {
				logger.info(bindingResults.getAllErrors());
				return super.REDIRECT + "/admin/sys/role/" + roleId;
			}
			Set<SysModule> modules = sysModuleService.findByModuleIdIn(Arrays.<Long>asList(moduleIds));
			role.setDescription(role.getDescription());
			role.setModules(modules);
			role = sysRoleService.save(role);
			return "sysRole";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	@RequestMapping(value = "/modules", method = RequestMethod.GET)
	public String modules(Model model, @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
			@RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			@RequestParam(value = "queryModuleName", required = false) String queryModuleName) throws WebException {
		try {
			Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "moduleId"));
			Page<SysModule> pager = sysModuleService.findAll(queryModuleName, pageable);
			model.addAttribute("queryModuleName", queryModuleName);
			model.addAttribute("pager", pager);
			return "sysModules";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	@RequestMapping(value = "/module/{moduleId}", method = RequestMethod.GET)
	public String module(Model model, @PathVariable Long moduleId) throws WebException {
		try {
			SysModule module = sysModuleService.findOne(moduleId);
			List<SysResource> resources = sysResourceService.findAll();
			model.addAttribute(RESOURCES, resources);
			model.addAttribute(MODULE, module);
			return "sysModule";
		} catch (Exception e) {
			throw new WebException(e, "", "");
		}
	}

	@RequestMapping(value = "/module", method = { RequestMethod.POST })
	public String module(Model model, @Valid SysModule module, BindingResult bindingResults,
			@RequestParam("resourceIds") Long[] resourceIds) throws WebException {
		try {
			long moduleId = module.getId() == null ? 0 : module.getId();
			if (bindingResults.hasErrors()) {
				logger.info(bindingResults.getAllErrors());
				return super.REDIRECT + "/admin/sys/module/" + moduleId;
			}
			Set<SysResource> resources = sysResourceService.findByResourceIdIn(Arrays.<Long>asList(resourceIds));
			module.setResources(resources);
			module = sysModuleService.save(module);
			return "sysModules";
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
	public String resources(Model model, @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
			@RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			@RequestParam(value = "queryResourcesName", required = false) String queryResourcesName)
			throws WebException {
		try {
			Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "resourceId"));
			Page<SysResource> pager = sysResourceService.findAll(queryResourcesName, pageable);
			model.addAttribute("queryResourcesName", queryResourcesName);
			model.addAttribute("pager", pager);
			return "sysResources";
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
			return "sysResource";
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
			return "sysResource";
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
