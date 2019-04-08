package com.store.service.serviceImp;

import java.sql.SQLException;
import java.util.List;

import com.store.dao.ProductDao;
import com.store.dao.daoImp.ProductDaoImpl;
import com.store.domain.PageModel;
import com.store.domain.Product;
import com.store.service.ProductService;

public class ProductServiceImpl implements ProductService{
	
	ProductDao dao = new ProductDaoImpl();
	
	@Override
	public List<Product> findByNew() throws SQLException {
		return dao.findByNew();
	}

	@Override
	public List<Product> findByHot() throws SQLException {
		return dao.findByHot();
	}

	@Override
	public Product findById(String pid) throws SQLException {
		return dao.findById(pid);
	}

	@Override
	public PageModel<Product> findByCidWithPage(String cid, int curNum) {
		try {
			int totalRecords = dao.findTotalRecordsByCid(cid);
			PageModel<Product> pm = new PageModel<>(curNum, 12, totalRecords);
			List<Product> list = dao.findAllByCid(cid,pm.getStartIndex(),pm.getPageSize());
			pm.setList(list);
			pm.setUrl("ProductServlet?method=findByCid&cid=" + cid);
			return pm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
