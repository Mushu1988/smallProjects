using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
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

namespace Calculator
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        bool isFirst=true;
        Button operation;
        double value1;
        double value2;
        double result;

        public MainWindow()
        {
            InitializeComponent();
            tb1stValue.Focus();
        }

        private void NumberButton_Click(object sender, RoutedEventArgs e)
        {
            Button button = sender as Button;

            string value = button.Content.ToString();
            if (isFirst)
            {
                tb1stValue.AppendText(value);
                tb1stValue.Focus();
                tb1stValue.CaretIndex = tb1stValue.Text.Length;
            }
            else
            {
                tb2ndValue.AppendText(value);
                tb2ndValue.Focus();
                tb2ndValue.CaretIndex = tb2ndValue.Text.Length;
            }
        }

        private void PlusMinus_ButtonClick(object sender, RoutedEventArgs e)
        {
            if (isFirst)
            {
                if (tb1stValue.Text.Substring(0, 1) == "-")
                {
                    tb1stValue.Text = tb1stValue.Text.Replace("-", "");
                }
                else tb1stValue.Text = "-" + tb1stValue.Text;
                tb1stValue.Focus();
                tb1stValue.CaretIndex = tb1stValue.Text.Length;
            }
            else
            {
                if (tb2ndValue.Text.Substring(0, 1) == "-")
                {
                    tb2ndValue.Text = tb2ndValue.Text.Replace("-", "");
                }
                else tb2ndValue.Text = "-" + tb2ndValue.Text;
                tb2ndValue.Focus();
                tb2ndValue.CaretIndex = tb2ndValue.Text.Length;
            }
        }

        private void NumberValidationTextBox(object sender, TextCompositionEventArgs e)
         {
            Regex regex = new Regex("^[-]?[0-9]*[\\.]?[0-9]*$");

            if (isFirst)
            {
                tb1stValue.Background = regex.IsMatch(e.Text) ? Brushes.LightGreen : Brushes.LightSalmon;
            }
            else tb2ndValue.Background = regex.IsMatch(e.Text) ? Brushes.LightGreen : Brushes.LightSalmon;

            e.Handled = (!regex.IsMatch(e.Text));
        }

        private void ClearAll_ButtonClick(object sender, RoutedEventArgs e)
        {
            tb1stValue.Text = "";
            tb2ndValue.Text = "";
            tb2ndValue.IsEnabled = false;
            lblOperation.Content = "";
            tbResult.Text = "";
            tbResult.IsEnabled = false;
            tb1stValue.IsEnabled = true;
            tb1stValue.Focus();
            btEquals.IsEnabled = false;
            isFirst = true;
        }

        private void Operations_ButtonClick(object sender, RoutedEventArgs e)
        {
            operation = sender as Button;

            if (!double.TryParse(tb1stValue.Text, out value1))
            {
                MessageBox.Show("Invalid number in 1st value field", "MyCalculator", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            tb1stValue.IsEnabled = false;
            tb2ndValue.IsEnabled = true;
            tb2ndValue.Focus();
            tb2ndValue.Background = Brushes.LightGreen;
            lblOperation.Content = operation.Content;
            isFirst = false;
        }

        private void Tb1stValue_TextChanged(object sender, TextChangedEventArgs e)
        {
            if (tb1stValue.Text.Length >= 1)
            {
                btAddition.IsEnabled = true;
                btDivide.IsEnabled = true;
                btExtraction.IsEnabled = true;
                btMultiply.IsEnabled = true;
            }
        }

        private void Equals_ButtonClick(object sender, RoutedEventArgs e)
        {
            if (!double.TryParse(tb2ndValue.Text, out value2))
            {
                MessageBox.Show("Invalid number in 1st value field", "MyCalculator", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            tb2ndValue.Background = Brushes.LightGreen;
            tb2ndValue.IsEnabled = false;

            switch (operation.Name)
            {
                case "btAddition":
                    result = value1 + value2;
                    break;
                case "btExtraction":
                    result = value1 - value2;
                    break;
                case "btMultiply":
                    result = value1 * value2;
                    break;
                case "btDivide":
                    if (value2 != 0)
                    {
                        result = value1 / value2;
                        break;
                    }
                    else
                    {
                        tb2ndValue.Background = Brushes.LightSalmon;
                        MessageBox.Show("Can not divide by 0!", "MyCalculator", MessageBoxButton.OK, MessageBoxImage.Error);
                        tb2ndValue.IsEnabled = true;
                        return;
                    }
                    
                default:
                    MessageBox.Show("Can not precoess the operation!", "MyCalculator", MessageBoxButton.OK, MessageBoxImage.Error);
                    break;
            }
            tbResult.Focus();
            tbResult.Background = Brushes.LightGreen;
            tbResult.Text = result.ToString();
        }

        private void Tb2ndValue_TextChanged(object sender, TextChangedEventArgs e)
        {
            if (tb2ndValue.Text.Length >= 1)
            {
                btEquals.IsEnabled = true;
                tbResult.IsEnabled = true;
                btAddition.IsEnabled = false;
                btDivide.IsEnabled = false;
                btExtraction.IsEnabled = false;
                btMultiply.IsEnabled = false;
            }
        }
    }
}
