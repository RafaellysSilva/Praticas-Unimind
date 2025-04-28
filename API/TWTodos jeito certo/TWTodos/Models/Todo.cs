using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace TWTodos.Models;

[Table("Usuario", Schema = "unimind")]
public class Todo
{
    [Key]
    public int IdUsuario { get; set; }
    public string Nome { get; set; }
    public string Email { get; set; }
    public string Senha { get; set; }
    public int IdNivel { get; set; }
    public int AcertosQuestoes { get; set; }
    public int ErrosQuestoes { get; set; }
    public int TempoEstudo { get; set; }
    public int CompeticoesRealizadas { get; set; }
}
