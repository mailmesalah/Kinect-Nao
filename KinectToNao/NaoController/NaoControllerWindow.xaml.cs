using Aldebaran.Proxies;
using System;
using System.Collections.Generic;
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

namespace NaoController
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();            
        }

        private void button_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                TextToSpeechProxy tts = new TextToSpeechProxy(textBoxIPAddress.Text.Trim().ToString(), 9559);
                tts.say("Hello World");

                MotionProxy motion = new MotionProxy(textBoxIPAddress.Text.Trim().ToString(), 9559);
                List<string> names = new List<string>();
                names.Add("Body");
                List<float> stifness = new List<float>();
                stifness.Add(1.0f);
                List<float> timel = new List<float>();
                timel.Add(1.0f);

                motion.stiffnessInterpolation(names, stifness, timel);

                RobotPostureProxy posture = new RobotPostureProxy(textBoxIPAddress.Text.Trim().ToString(), 9559);
                posture.goToPosture("StandInit", 0.5f);
                // Example showing the moveTo command
                // as length of path is less than 0.4m
                // the path will be an SE3 interpolation
                // The units for this command are meters and radians
                float x = 0.2f;
                float y = 0.2f;
                // pi/2 anti-clockwise (90 degrees)
                //float theta = 1.5709f;
                float theta = 0.0f;
                motion.moveTo(x, y, theta);
                // Will block until walk Task is finished

                // Example showing the moveTo command
                // as length of path is more than 0.4m
                // the path will be follow a dubins curve
                // The units for this command are meters and radians
                /*x = -0.1f;
                y = -0.5f;
                theta = 0.0f;
                motion.moveTo(x, y, theta);
                */
                Console.WriteLine(motion.getSummary());
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
