package oa.service.impl

import oa.entity.PageRes
import oa.repository.PageResRepository
import oa.service.EmployeeService
import oa.service.PageResService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class PageResServiceImpl @Autowired constructor(
    private val repository: PageResRepository,
    private val employeeService: EmployeeService
) : PageResService {
    override fun getPageResOfCurrentUser(): Set<PageRes> {
        val pageRes: MutableSet<PageRes> = HashSet()

        val jobNumber = SecurityContextHolder.getContext().authentication.name
        val employee = employeeService.findByJobNumber(jobNumber)
        if (employee != null) {
            for (role in employee.roles) {
                pageRes.addAll(role.pageRes)
            }
        }
        return pageRes
    }

    override fun findAll(pageable: Pageable): Page<PageRes> = repository.findAll(pageable)
}