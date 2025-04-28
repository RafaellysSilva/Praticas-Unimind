namespace TWTodos.Models;

public class Todo{

    //deClara os atributos
    private int id;
    private string? name;
    private DateOnly date;
    private bool isCompleted;

    //Declara as propriedades
    public int Id { get; set; }
    public required string Name { get; set; } = string.Empty;
    public DateOnly Date { get; set; }
    public bool IsCompleted { get; set; }

}