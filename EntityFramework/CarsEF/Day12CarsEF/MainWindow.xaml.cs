using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Day12CarsEF
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        Car selectedCar;

        public MainWindow()
        {
            try
            {
                InitializeComponent();
                Globals.ctx = new CarDBContext();
                lvCars.ItemsSource = (from f in Globals.ctx.Cars select f).ToList<Car>();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Fatal error: " + ex.Message);
                Close();
            }
        }

        private void AddCar_MenuClick(object sender, RoutedEventArgs e)
        {
            AddEditCarDialog dialog = new AddEditCarDialog(this, selectedCar);
            if (dialog.ShowDialog() == true)
            {
                lvCars.ItemsSource = (from f in Globals.ctx.Cars select f).ToList<Car>();
            }
        }

        private void DeleteFriend_ButtonClick(object sender, RoutedEventArgs e)
        {
            if (selectedCar == null) return; // should never happen
            MessageBoxResult result = MessageBox.Show("Are you sure you want to delete this record?\n" + selectedCar, Globals.AppName, MessageBoxButton.OKCancel, MessageBoxImage.Question, MessageBoxResult.Cancel);
            if (result == MessageBoxResult.OK)
            {
                try
                {
                    Globals.ctx.Cars.Remove(selectedCar); // schedule for deletion
                    Globals.ctx.SaveChanges();
                }
                catch (DataException ex)
                { // TODO: make message box nicer
                    MessageBox.Show("Database error:\n" + ex.Message);
                }
                catch (SystemException ex)
                { // TODO: make message box nicer
                    MessageBox.Show("Database error:\n" + ex.Message);
                }
            }
            lvCars.ItemsSource = (from f in Globals.ctx.Cars select f).ToList<Car>();
        }

        private void FileExit_MenuClick(object sender, RoutedEventArgs e)
        {
            Close();
        }

        private void LvCars_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            selectedCar = lvCars.SelectedItem as Car;
        }

        private void FileExportSelected_ButtonClick(object sender, RoutedEventArgs e)
        {
            var selectedItemsCollection = lvCars.SelectedItems;
            if (selectedItemsCollection.Count == 0)
            { // TODO: make MB nicer
                MessageBox.Show("Select some records first");
            }

            SaveFileDialog sfd = new SaveFileDialog();
            sfd.Filter = "Text file (*.txt)|*.txt|Any file (*.*)|*.*";
            sfd.ShowDialog();
            if (sfd.FileName != "")
            {
                List<string> linesList = new List<string>();
                foreach (var item in selectedItemsCollection)
                {
                    Car c = item as Car;
                    linesList.Add($"{c.Id};{c.MakeModel};{c.EngineSizeL};{c.FuelType}");
                }
                try
                {
                    File.WriteAllLines(sfd.FileName, linesList);
                }
                catch (IOException ex)
                {
                    MessageBox.Show("Error saving to file:\n" + ex.Message);
                }
            }
        }
    }
}
