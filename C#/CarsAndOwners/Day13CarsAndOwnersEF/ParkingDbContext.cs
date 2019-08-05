using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Day13CarsAndOwnersEF
{
    public class ParkingDbContext : DbContext
    {
        virtual public DbSet<Car> Cars { get; set; }
        virtual public DbSet<Owner> Owners { get; set; }
    }
}
