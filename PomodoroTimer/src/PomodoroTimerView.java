
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javazoom.jl.player.Player;

public class PomodoroTimerView {

    private JFrame frame;
    private JLabel lblBemVindo, lblTimer, lblNotificacao, lblVersao;
    private JPanel panel;
    private JButton iniciar, pausar;
    private Timer timer;
    private File file;

    String addPrefixSeg, addPrefixMin, addTimer;

    PomodoroTimer pomodoro = new PomodoroTimer(59, 24, true);

    //construct
    public PomodoroTimerView() {

        frame = new JFrame();

        lblBemVindo = new JLabel("Pomodoro Timer", SwingConstants.CENTER);
        lblBemVindo.setFont(new java.awt.Font("Arial", Font.PLAIN, 30));

        lblNotificacao = new JLabel("", SwingConstants.CENTER);
        lblNotificacao.setFont(new java.awt.Font("Arial", Font.ITALIC, 16));

        lblTimer = new JLabel("25:00", SwingConstants.CENTER);
        lblTimer.setFont(new java.awt.Font("Arial", Font.ITALIC, 30));

        lblVersao = new JLabel("1.5", SwingConstants.CENTER);
        lblVersao.setFont(new java.awt.Font("Arial", Font.ITALIC, 12));

        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(10, 70, 10, 70));
        panel.setLayout(new GridLayout(0, 1));

        panel.add(lblBemVindo);
        panel.add(lblNotificacao);
        panel.add(lblTimer);

        iniciar = new JButton("Iniciar");

        pausar = new JButton("Pausar");

        panel.add(iniciar);
        panel.add(pausar);
        pausar.setEnabled(false);
        panel.add(lblVersao);

        frame.setSize(550, 300);
        frame.add(panel, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Pomodoro Timer");
        frame.setVisible(true);

        //timer, executa a cada 1 segundo
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //adicionar prefixo 0
                addPrefixSeg = (pomodoro.getSegundos() < 10) ? "0" + pomodoro.getSegundos() : Integer.toString(pomodoro.getSegundos());

                addPrefixMin = (pomodoro.getMinutos() < 10) ? "0" + pomodoro.getMinutos() : Integer.toString(pomodoro.getMinutos());

                addTimer = addPrefixMin + ":" + addPrefixSeg;

                //se os segundos forem maior q zero, diminuir os segundos
                if (pomodoro.getSegundos() > 0) {
                    lblTimer.setText(addTimer);
                    pomodoro.subSegundos();
                } else if (pomodoro.getSegundos() == 0 && pomodoro.getMinutos() >= 0) {//quando segundos chegar a 0 reiniciar o número de segundos e diminuir a quantidade de minutos
                    lblTimer.setText(addTimer);
                    pomodoro.setSegundos(59);
                    pomodoro.subMinutos();
                }

                /*quando os minutos forem menor 0 e for igual a 59 
                (por conta do if anterior), mudar o texto para o timer zerado,
                trocar os segundos e os minutos para -1, para q no proximo
                ciclo do Timer ir para o tempo de descanso ou de trabalho
                 */
                if (pomodoro.getMinutos() < 0 && pomodoro.getSegundos() == 59) {
                    lblTimer.setText("00:00");
                    pomodoro.setSegundos(-1);
                    pomodoro.setMinutos(-1);

                } else if (pomodoro.getMinutos() < 0 && pomodoro.getSegundos() < 0 && pomodoro.getCiclo() == true) {
                    alarm("songs/alarm-song.mp3");
                    lblTimer.setText("05:00");
                    pomodoro.setSegundos(59);
                    pomodoro.setMinutos(4);
                    lblNotificacao.setVisible(true);
                    lblNotificacao.setText("Você completou um ciclo de trabalho, iniciar descanço");
                    pomodoro.setCiclo(false);
                    iniciar.setEnabled(true);
                    pausar.setEnabled(false);
                    timer.stop();
                } else if (pomodoro.getMinutos() < 0 && pomodoro.getSegundos() < 0 && pomodoro.getCiclo() == false) {//quando minutos chegar a 0 e o ciclo for igual a false, a hora de descanso vai estar completa
                    alarm("songs/alarm-song.mp3");
                    lblTimer.setText("25:00");
                    pomodoro.setSegundos(59);
                    pomodoro.setMinutos(24);
                    lblNotificacao.setVisible(true);
                    lblNotificacao.setText("Você completou um ciclo de descanso, iniciar trabalho");
                    pomodoro.setCiclo(true);
                    iniciar.setEnabled(true);
                    pausar.setEnabled(false);
                    timer.stop();
                }
            }
        });

        //iniciar Pomodoro
        iniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
                iniciar.setEnabled(false);
                pausar.setEnabled(true);
                lblNotificacao.setVisible(false);
            }
        });

        //pausar pomodor
        pausar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                timer.stop();
                pausar.setEnabled(false);
                iniciar.setEnabled(true);
                lblNotificacao.setVisible(true);
                lblNotificacao.setText("Timer Pausado");
            }

        });

    }

    public void alarm(String filepath) {
        try {
            File file = new File(filepath);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            try {
                Player player = new Player(bis);
                player.play();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "alerta", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "alerta", JOptionPane.ERROR_MESSAGE);
        }
    }
}
