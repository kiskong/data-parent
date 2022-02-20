package com.cingk.datameta.service.intf;

import com.cingk.datameta.model.InterfaceEntity;

import java.util.List;

/**
 * 抽象的服务接口，基本的增、删、改、查操作 具体的服务接口可选择实现
 */

public interface IBaseService {

	InterfaceEntity save(InterfaceEntity interfaceEntity);

	void delete(InterfaceEntity interfaceEntity);

	void deleteById(Integer id);

	void modify(InterfaceEntity interfaceEntity);

	InterfaceEntity query(InterfaceEntity interfaceEntity);

	List<InterfaceEntity> queryAll();

}
