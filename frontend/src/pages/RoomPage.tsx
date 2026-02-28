import { useParams, useNavigate } from 'react-router-dom';
//import toast from 'react-hot-toast';
import styles from './RoomPage.module.css';
import toast from 'react-hot-toast';

const RoomPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const isLoggedIn = false; 

  // Simulação dos dados baseados na sua tabela SQL
  console.log("Estilos carregados:", styles);
  const roomSpec = {
    id: 1,
    numero: "101-A",
    status: "Disponível",
    area: "45.50",
    categoria: "Suíte Master",
    descricao: "Vista para o mar, frigobar, ar-condicionado e Wi-Fi de alta velocidade.",
    preco: 450.00
  };

  const handleBooking = () => {
    navigate(`/reserva/confirmacao/${id}`);
    if (!isLoggedIn) {
      toast.error("Você precisa estar logado para reservar!");
      navigate('/login');
    } else {
      navigate(`/reserva/confirmacao/${id}`);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.grid}>
        <section className={styles.imageSection}>
          <img src="https://images.unsplash.com/photo-1590490360182-c33d57733427" alt="Quarto" />
        </section>

        <section className={styles.infoSection}>
          <span className={styles.badge}>{roomSpec.status}</span>
          <h1 className={styles.title}>{roomSpec.categoria}</h1>
          <p className={styles.roomNumber}>Quarto n° {roomSpec.numero}</p>
          
          <div className={styles.specs}>
            <p><strong>Área:</strong> {roomSpec.area} m²</p>
            <p><strong>Configuração:</strong> {roomSpec.descricao}</p>
          </div>

          <div className={styles.priceAction}>
            <span className={styles.price}>R${roomSpec.preco} / noite</span>
            <button className={styles.bookButton} onClick={handleBooking}>
              RESERVAR AGORA
            </button>
          </div>
        </section>
      </div>
    </div>
  );
};

export default RoomPage;