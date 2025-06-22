using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

[Table("Questao", Schema = "unimind")]
public class Questao
{
    [Key]
    [Column("idQuestao")]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int IdQuestao { get; set; }

    [Column("ano")]
    public int Ano { get; set; }

    [Column("idCategoria")]
    public int IdCategoria { get; set; }

    [Column("fonte")]
    public string Fonte { get; set; }

    [Column("questao")]
    public string Pergunta { get; set; }

    // O campo "Resposta" foi removido. A resposta correta será indicada
    // pelo campo "Correta" na classe Alternativa.

    // Adiciona a relação com as alternativas
    public virtual ICollection<Alternativa> Alternativas { get; set; }

    // Inicializa a coleção para evitar erros de referência nula
    public Questao()
    {
        Alternativas = new HashSet<Alternativa>();
    }
}