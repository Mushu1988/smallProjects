using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Day13CarsAndOwnersEF
{
    class Program
    {
        static ParkingDbContext ctx;

        static void Main(string[] args)
        {
             ctx = new ParkingDbContext();

            int choice;
            do
            {
                choice = GetMenuChoice();
                switch (choice)
                {
                    case 1:
                        ListCarsAndOwners();
                        break;
                    case 2:
                        ListOwnersAndCars();
                        break;
                    case 3:
                        AddCar();
                        break;
                    case 4:
                        AddOwner();
                        break;
                    case 5:
                        AssignCarToOwner();
                        break;
                    case 6:
                        DeleteOwnerAndHisCars();
                        break;
                    case 0: // exit
                        break;
                    default: // ALWAYS have a default handler in switch/case
                        Console.WriteLine("Invalid choice try again.");
                        break;
                }
                Console.WriteLine();
            } while (choice != 0);

        }

        static int GetMenuChoice()
        {
            Console.Write(
            @"What do you want to do?
            1. List all cars and their owner
            2. List all owners and their cars
            3. Add a car (no owner)
            4. Add an owner (no cars)
            5. Assign car to an owner (or no owner)
            6. Delete an owner with all cars they own
            0. Quit
            Choice: ");
            try
            {
                string choiceStr = Console.ReadLine();
                int choice = int.Parse(choiceStr);
                return choice;
            }
            catch (Exception ex)
            {
                if (ex is FormatException | ex is OverflowException)
                {
                    Console.WriteLine("Invalid value entered\n");
                }
                else throw ex; // if we don't handle an exception we MUST throw it!
            }
            return -1;
        }

        static void ListCarsAndOwners()
        {
            var carsCollection = ctx.Cars
                   .Include("Owner");

            foreach( var car in carsCollection)
            {
                if (car.Owner != null)
                {
                    Console.WriteLine(car + " belongs to " + car.Owner.Name);
                }
                else Console.WriteLine(car + " has no owner");
            }
        }

        static void ListOwnersAndCars()
        {
            var ownersCollection = ctx.Owners;
            foreach (var owner in ownersCollection)
            {
                Console.WriteLine(owner);
                    foreach (Car c in owner.CarsCollection) {
                    Console.WriteLine(" owns " + c.MakeModel + ", " + c.YearOfProd);
                }
            }
        }

        static void AddCar()
        {
            Console.WriteLine("Please, enter the make and model:");
            string makeModel = Console.ReadLine();
            Console.WriteLine("Please, enter the year of production:");
            string yearStr = Console.ReadLine();
            if(!int.TryParse(yearStr, out int year))
            {
                Console.WriteLine("The year of production should be a valid integer");
                return;
            }
            try
            {
                Car car = new Car() { MakeModel = makeModel, YearOfProd = year };
                ctx.Cars.Add(car);
                ctx.SaveChanges();
                Console.WriteLine("Person added.");
            }
            catch (ArgumentException ex)
            {
                Console.WriteLine("Person data invalid: " + ex.Message);
            }
            catch(DataException ex)
            {
                Console.WriteLine("Database problem: " + ex.Message);
            }
            catch (SystemException ex)
            {
                Console.WriteLine("Database problem: " + ex.Message);
            }
        }

        static void AddOwner()
        {
            Console.WriteLine("Please, enter the name of the owner:");
            string name = Console.ReadLine();
            try
            {
                Owner owner = new Owner() { Name = name };
                ctx.Owners.Add(owner);
                ctx.SaveChanges();
            }
            catch (ArgumentException ex)
            {
                Console.WriteLine("Person data invalid: " + ex.Message);
            }
            catch (DataException ex)
            {
                Console.WriteLine("Database problem: " + ex.Message);
            }
            catch (SystemException ex)
            {
                Console.WriteLine("Database problem: " + ex.Message);
            }
        }

        static void AssignCarToOwner()
        {
            Console.WriteLine("Please, enter the Id of the owner that you want to assign a car to:");
            var ownerCollection = (from o in ctx.Owners select o).ToList<Owner>();
            foreach (var owner in ownerCollection)
            {
                Console.WriteLine(owner);
            }

            string idStr = Console.ReadLine();
            if (!int.TryParse(idStr, out int id))
            {
                Console.WriteLine("Wrong value entered");
                return;
            }

            Console.WriteLine("Please, enter the Id of the car that you want to assign an owner to:");
            var carCollection = (from c in ctx.Cars select c).ToList<Car>();
            foreach (var car in carCollection)
            {
                Console.WriteLine(car);
            }

            string idCarStr = Console.ReadLine();
            if (!int.TryParse(idCarStr, out int idCar))
            {
                Console.WriteLine("Wrong value entered");
                return;
            }

            var selectedOwner = ctx.Owners.Find(id);
            var selectedCar = ctx.Cars.Find(idCar);

            if (selectedOwner != null && selectedCar != null)
            {
                //selectedOwner.CarsCollection.Add(selectedCar);
                selectedCar.Owner = selectedOwner;
                ctx.SaveChanges();
            }
            else
            {
                Console.WriteLine("One or both selected objects do not exist");
                return;
            }

        }

        static void DeleteOwnerAndHisCars()
        {
            Console.WriteLine("Please, enter the Id of the owner that you want to assign a car to:");
            var ownerCollection = (from o in ctx.Owners select o).ToList<Owner>();
            foreach (var owner in ownerCollection)
            {
                Console.WriteLine(owner);
            }

            string idStr = Console.ReadLine();
            if (!int.TryParse(idStr, out int id))
            {
                Console.WriteLine("Wrong value entered");
                return;
            }

            var ownerToDelete = ctx.Owners.Find(id);
            foreach (Car c in ownerToDelete.CarsCollection.ToList<Car>())
            {
                ctx.Cars.Remove(c);
            }
            /*var carsToDelete = (from c in ctx.Cars where c.Owner.Id == ownerToDelete.Id select c).ToList<Car>();
            foreach (Car c in carsToDelete)
            {
                ctx.Cars.Remove(c);
            }*/

            ctx.Owners.Remove(ownerToDelete);
            ctx.SaveChanges();
        }

     }
}
