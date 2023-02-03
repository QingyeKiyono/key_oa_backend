package oa.service.impl

import oa.dto.LoginEmployee
import oa.entity.Permission
import oa.entity.Role
import oa.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl @Autowired constructor(private val employeeService: EmployeeService) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(jobNumber: String): UserDetails {
        // 根据登录的工号查询员工信息
        val employee = employeeService.findByJobNumber(jobNumber)
            ?: throw UsernameNotFoundException(String.format("No employee found with job number: %s.", jobNumber))
        val reachableRoles = getReachableRoles(employee.roles)

        // 把员工信息封装成UserDetails对象返回
        return LoginEmployee(employee, getAuthorities(reachableRoles))
    }

    private fun getReachableRoles(roles: Collection<Role>): Set<Role> {
        if (roles.isEmpty()) {
            return emptySet()
        }

        val unprocessedRoles: MutableSet<Role> = HashSet(10)
        unprocessedRoles.addAll(roles)
        val reachableRoles: MutableSet<Role> = HashSet(20)
        while (unprocessedRoles.isNotEmpty()) {
            for (role in unprocessedRoles) {
                reachableRoles.add(role)
                unprocessedRoles.remove(role)
                unprocessedRoles.addAll(role.children)
            }
        }
        return reachableRoles
    }

    private fun getAuthorities(roles: Set<Role>): List<String> {
        if (roles.isEmpty()) {
            return emptyList()
        }

        val authorities: MutableList<Permission> = mutableListOf()
        roles.forEach { authorities.addAll(it.permissions) }
        return authorities.map { it.value }.toList()
    }
}