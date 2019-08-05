using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Day13TravelsEF
{
    public class Passenger
    {
        public int Id { get; set; }

        [Required]
        [StringLength(50)]
        public string Name { get; set; }

        public Gender Gender;

        public virtual Train Train { get; set; } // relation, may be null

        public override string ToString()
        {
            string trainStr =Train == null ? "unknown" : Train.Number.ToString();
            return $"Passenger({Id}): {Name} ({Gender}) travels on the train number {trainStr}";
        }
    }
    public enum Gender { Male = 1, Female = 2, NA = 3 };
}
