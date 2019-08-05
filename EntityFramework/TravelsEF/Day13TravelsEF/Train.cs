using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Day13TravelsEF
{
    public class Train
    {
        public int Id { get; set; }

        [NotMapped]
        private int _number;

        public int Number
        {
            get
            {
                return _number;
            }
            set
            {
                if (value < 1 || value > 999)
                {
                    throw new ArgumentException("Number should be between 1 and 999");
                }
                else _number = value;
            }
        }

        [Column("Date", TypeName = "Date")][Required]
        public DateTime Date { get; set; }

        public virtual ICollection<Passenger> passCollection { get; set; } // relation

        public string GetPassenger
        {
            get
            {
                List<string> namesList = null;
                if (passCollection.Count >= 1)
                {
                    foreach (Passenger p in passCollection)
                    {
                        namesList.Add(p.Name);
                    }

                    if (namesList != null)
                    {
                        string passlist = string.Join(",", namesList);
                        return passlist;
                    }
                    else return "";
                }
                else return "";
            }
            set {; }
        }

        public override string ToString()
        {
            return $"Train {Number} departs on {Date}"; 
        }
    }
}
