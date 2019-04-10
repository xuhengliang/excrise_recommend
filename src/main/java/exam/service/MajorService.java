package exam.service;

import exam.model.Major;
import exam.service.base.BaseService;

import java.util.List;

public interface MajorService extends BaseService<Major> {

	/**
	 * 根据专业名称搜索
	 * @param name 专业名称
	 * @return 找不到返回null
	 */
	Major findByName(String name);
	
	/**
	 * 根据年级查找
	 * @param grade 年级id
	 */
	List<Major> findByGrade(int grade);
	
	/**
	 * 获取所有专业
	 */
	List<Major> findAll();
	
}
