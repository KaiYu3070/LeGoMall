package k.kk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import k.kk.domain.TbSeller;
import k.kk.entity.Result;
import k.kk.service.SellerService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for managing sellers
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    /**
     * Return all sellers
     *
     * @return Result
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<TbSeller> list = sellerService.findAll();
            return new Result(true, "Query successful", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Query failed");
        }
    }

    /**
     * Pagination query for sellers
     *
     * @param pageNum Current page number
     * @param pageSize Number of items per page
     * @return Result with page information
     */
    @RequestMapping("/findPage/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        try {
            PageInfo<TbSeller> page = sellerService.findPage(pageNum, pageSize);
            return new Result(true, "Query successful", page.getTotal(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Query failed");
        }
    }

    /**
     * Seller registration
     *
     * @param seller Seller information
     * @return Result
     */
    @RequestMapping("/save")
    public Result save(@RequestBody TbSeller seller) {

        System.out.println(seller);
        try {

            // Encrypt password using BCrypt
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            seller.setPassword(encoder.encode(seller.getPassword()));

            // Set the seller's status to 0 (not approved)
            seller.setStatus("0");

            // Call service to save the seller
            sellerService.add(seller);
            return new Result(true, "Add successful");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Add failed");
        }
    }

    /**
     * Update seller information
     *
     * @param seller Seller information
     * @return Result
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbSeller seller) {
        try {
            sellerService.update(seller);
            return new Result(true, "Update successful");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Update failed");
        }
    }

    /**
     * Get a single seller by ID
     *
     * @param id Seller ID
     * @return Result with seller data
     */
    @RequestMapping("/findOne")
    public Result findOne(String id) {
        try {
            TbSeller seller = sellerService.findOne(id);
            if (seller != null && seller.getSellerId() != null && !seller.getSellerId().trim().isEmpty()) {
                return new Result(true, "Query successful");
            }
            return new Result(false, "Query failed");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Query failed");
        }
    }

    /**
     * Search for current user's name
     *
     * @return Result with the current user's name
     */
    @RequestMapping("/searchMyName")
    public Result searchMyName() {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            String name = context.getAuthentication().getName();
            return new Result(true, "Operation successful", name);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Operation failed");
        }
    }

    /**
     * Batch delete sellers by IDs
     *
     * @param ids Seller IDs
     * @return Result
     */
    @RequestMapping("/delete/{ids}")
    public Result delete(@PathVariable("ids") String[] ids) {
        try {
            sellerService.delete(ids);
            return new Result(true, "Delete successful");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Delete failed");
        }
    }

    /**
     * Search and paginate sellers based on parameters
     *
     * @param seller Seller filter parameters
     * @param pageNum Current page number
     * @param pageSize Number of items per page
     * @return Result with pagination data
     */
    @RequestMapping("/search/{pageNum}/{pageSize}")
    public Result search(@RequestBody TbSeller seller, @PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        try {
            PageInfo<TbSeller> page = sellerService.findPage(seller, pageNum, pageSize);
            return new Result(true, "Query successful", page.getTotal(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Query failed");
        }
    }

    /**
     * Edit seller information
     *
     * @param seller Seller information
     * @return Result
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody TbSeller seller) {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            String name = context.getAuthentication().getName();
            TbSeller currentSeller = sellerService.findOne(name);
            seller.setSellerId(name);
            seller.setPassword(currentSeller.getPassword());
            seller.setStatus(currentSeller.getStatus());
            sellerService.update(seller);
            return new Result(true, "Operation successful");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Operation failed");
        }
    }

    /**
     * Validate old password
     *
     * @param oldPass Current password
     * @return Result
     */
    @RequestMapping("/oldPass/{oldPassNum}")
    public Result validateOldPassword(@PathVariable("oldPassNum") String oldPass) {
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean matches = encoder.matches(oldPass, sellerService.findOne(SecurityContextHolder.getContext().getAuthentication().getName()).getPassword());

            if (matches) {
                return new Result(true, "Operation successful");
            }
            return new Result(false, "Operation failed");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Operation failed");
        }
    }

    /**
     * Change seller password
     *
     * @param newPass1 New password
     * @return Result
     */
    @RequestMapping("/editPass01/{newPass1}")
    public Result changePassword(@PathVariable String newPass1) {
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPass = encoder.encode(newPass1);

            TbSeller seller = sellerService.findOne(SecurityContextHolder.getContext().getAuthentication().getName());
            seller.setPassword(encodedPass);
            sellerService.update(seller);

            return new Result(true, "Operation successful");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Operation failed");
        }
    }

}
