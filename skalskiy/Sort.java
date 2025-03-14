import java.util.Comparator;
import java.util.List;


public class Sort
{
    public static void sortTasksByDueDate(List<Task> tasks)
    {
        tasks.sort(Comparator.comparing(task -> task.dueDate));
        System.out.println("Tasks sorted by due date.");
    }

    public static void sortTasksByStatus(List<Task> tasks)
    {
        tasks.sort(Comparator.comparing(task -> task.status));
        System.out.println("Tasks sorted by status.");
    }
}


