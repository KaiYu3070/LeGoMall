package k.kk.controller;

// Controller for specifications

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import k.kk.domain.Brand;
import k.kk.domain.TbSpecification;
import k.kk.entity.Result;
import k.kk.groupentity.Specification;
import k.kk.searchPage.search;
import k.kk.service.SpecificationService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class SpecificationController {

    // Injecting the service
    @Reference
    private SpecificationService specificationService;

    /*
     * Paginated query
     * */
    @RequestMapping("/findPage/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        try {
            // Call the service layer for querying
            PageInfo<TbSpecification> pageInfo = specificationService.findPage(pageNum, pageSize);
            for (TbSpecification tbSpecification : pageInfo.getList()) {
                System.out.println(tbSpecification.getSpecName());
            }
            return new Result(true, "Query successful", pageInfo.getTotal(), pageInfo.getList());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Query failed");
        }
    }

    /*
     * Save specification
     * */
    @RequestMapping("/save")
    public Result save(@RequestBody Specification specification) {
        try {
            specificationService.save(specification);
            return new Result(true, "Operation successful");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Operation failed");
        }
    }

    /*
     * Get specification by primary key
     * */
    @RequestMapping("/findOne/{id}")
    public Result findOne(@PathVariable("id") Long id) {
        try {
            // Get specification by primary key
            Specification specification = specificationService.findOne(id);
            return new Result(true, "Operation successful", specification);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Operation failed");
        }
    }

    /*
     * Update specification
     * */
    @RequestMapping("/update")
    public Result update(@RequestBody Specification specification) {
        try {
            specificationService.update(specification);
            return new Result(true, "Operation successful");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Operation failed");
        }
    }

    /*
     * Batch delete
     * */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            specificationService.delete(ids);
            return new Result(true, "Operation successful");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Operation failed");
        }
    }

    /*
     * Provide specifications data when adding a template
     * */
    @RequestMapping("/findSpecificationList")
    public Result findSpecificationList() {
        try {
            // Create a list to hold the results
            List<Map> list = new ArrayList<>();
            // Query all specification data and assemble it into a list
            List<TbSpecification> specifications = specificationService.findAll();

            // Assemble data
            for (TbSpecification specification : specifications) {
                Map map = new HashMap();
                map.put("id", specification.getId());
                map.put("text", specification.getSpecName());
                list.add(map);
            }

            // Success
            return new Result(true, "Operation successful", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Operation failed");
        }
    }

    /*
     * Conditional search
     * */
    @RequestMapping("/searchPage/{pageNum}/{pageSize}/{guige}")
    public Result searchPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize,
                             @PathVariable("guige") String guige) {
        search search = new search();

        if (!"null".equals(guige)) {
            search.setBname(guige);
        }

        try {
            // Call the service layer
            PageInfo<TbSpecification> pageInfo = specificationService.searchPage(pageNum, pageSize, search);

            System.out.println("---------------Success--------------");
            for (TbSpecification specification : pageInfo.getList()) {
                System.out.println(specification);
            }
            return new Result(true, "Query successful", pageInfo.getTotal(), pageInfo.getList());
        } catch (Exception e) {
            System.out.println("----------------Error-------------");
            e.printStackTrace();
            return new Result(false, "Query failed");
        }
    }
}
