using System;
using System.Collections.Generic;
using System.Data;
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
using System.Windows.Shapes;

namespace Day13TravelsEF
{
    /// <summary>
    /// Interaction logic for AddEditTrainDialog.xaml
    /// </summary>
    public partial class AddEditTrainDialog : Window
    {
       Train selectedTrain;

        public AddEditTrainDialog(MainWindow owner, Train train = null)
        {
            Owner = owner;
            selectedTrain = train;
            InitializeComponent();
            btSave.Content = selectedTrain == null ? "Add train" : "Update train";
            if (selectedTrain != null)
            {
                lblId.Content = selectedTrain.Id;
                tbNumber.Text = selectedTrain.Number.ToString();
                dpDate.SelectedDate = selectedTrain.Date.Date;
            }
        }

        private void BtSave_Click(object sender, RoutedEventArgs e)
        {
            string numberStr = tbNumber.Text;
            if(!int.TryParse(numberStr, out int number))
            {
                MessageBox.Show("Error: Train number should be a valid integer", Globals.AppName, MessageBoxButton.OK, MessageBoxImage.Error);
            }

            DateTime date = dpDate.SelectedDate.Value.Date;

            if (selectedTrain != null)
            {
                try
                {
                    selectedTrain.Number = number;
                    selectedTrain.Date = date;
                    Globals.ctx.SaveChanges();
                }
                catch (DataException ex)
                { 
                    MessageBox.Show("Database error:\n" + ex.Message, Globals.AppName, MessageBoxButton.OK, MessageBoxImage.Error);
                }
                catch (SystemException ex)
                { 
                    MessageBox.Show("Database error:\n" + ex.Message, Globals.AppName, MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
            else
            {
                try
                {
                    Train train = new Train() { Number=number, Date=date };
                    Globals.ctx.Trains.Add(train);
                    Globals.ctx.SaveChanges();
                    DialogResult = true;
                }
                catch (DataException ex)
                {
                    MessageBox.Show("Database error:\n" + ex.Message, Globals.AppName, MessageBoxButton.OK, MessageBoxImage.Error);
                }
                catch (SystemException ex)
                {
                    MessageBox.Show("Database error:\n" + ex.Message, Globals.AppName, MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
        }


    }
}
