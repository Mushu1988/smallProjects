using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Day13CarsAndOwnersEF
{
    public class Owner
    { // table Owners
        public int Id { get; set; }

        [NotMapped]
        private string _name;

        [StringLength(100)]
        [Required]
        public string Name
        {
            get
            {
                return _name;
            }
            set
            {
                if (value.Length > 100)
                {
                    throw new ArgumentException("The name should nor exceed 100 characters");
                }
                else
                {
                    _name = value;
                }
            }
        }  // up to 100 characters

        
        public virtual ICollection<Car> CarsCollection { get; set; } // relation

        public override string ToString()
        {
            return $"{Id}: {Name}";
        }
    }
}
