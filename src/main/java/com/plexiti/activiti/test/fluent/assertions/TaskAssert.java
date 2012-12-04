package com.plexiti.activiti.test.fluent.assertions;

import com.plexiti.activiti.test.fluent.ActivitiFluentTestHelper;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;

/**
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public class TaskAssert extends AbstractAssert<TaskAssert, Task> {

    protected TaskAssert(Task actual) {
        super(actual, TaskAssert.class);
    }

    public static TaskAssert assertThat(Task actual) {
        return new TaskAssert(actual);
    }

    public TaskAssert isUnassigned() {
        isNotNull();

        Assertions.assertThat(actual.getAssignee())
                .overridingErrorMessage("Expected task '%s' to be unassigned but it is assigned to '%s'",
                                        actual.getName(), actual.getAssignee())
                .isNull();

        return this;
    }

    public TaskAssert isAssignedTo(String userId) {
        isNotNull();

        Assertions.assertThat(actual.getAssignee())
                .overridingErrorMessage("Expected task '%s' to be assigned to user '%s' but it is assigned to '%s'",
                                        actual.getName(), userId, actual.getAssignee())
                .isEqualTo(userId);

        return this;
    }

    public TaskAssert hasCandidateGroup(String candidateGroupId) {
        isNotNull();

        TaskService taskService = ActivitiFluentTestHelper.getTaskService();
        TaskQuery query = taskService.createTaskQuery()
                                     .taskId(actual.getId()).taskCandidateGroup(candidateGroupId);
        Assertions.assertThat(query.singleResult())
                .overridingErrorMessage("Expected task '%s' to have '%s' as a candidate group",
                                        actual.getName(), candidateGroupId);

        return this;
    }
}
