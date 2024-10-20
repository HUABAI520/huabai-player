package com.ithe.huabaiplayer.task.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ithe.huabaiplayer.task.model.entity.Task;
import com.ithe.huabaiplayer.task.mapper.TaskMapper;
import com.ithe.huabaiplayer.task.service.TaskService;
import org.springframework.stereotype.Service;

/**
 * 任务表 服务层实现。
 *
 * @author L
 * @since 2024-09-24
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

}
