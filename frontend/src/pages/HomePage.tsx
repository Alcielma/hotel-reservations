import { useState } from "react";
import DateInput from "../components/ui/Date";
import { Search } from "lucide-react";
import styles from "./HomePage.module.css";

const Home = () => {
  const [booking, setBooking] = useState({
    checkIn: "",
    checkOut: "",
  });

  const handleChange = (event: { target: { name: string; value: string } }) => {
    const { name, value } = event.target;
    setBooking((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <div className={styles.homeContainer}>
      {/* --- BANNER PRINCIPAL --- */}
      <section className={styles.heroSection}>
        <img
          src="https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80"
          alt="Horizon Hotel Exterior"
          className={styles.heroImage}
        />
        <div className={styles.heroOverlay}></div>
      </section>

      {/* --- SEÇÃO DE BUSCA (DISPONIBILIDADE) --- */}
      <section className={styles.searchSection}>
        <div className={styles.searchContent}>
          <h2 className={styles.title}>
            Período da Acomodação
          </h2>

          <div className={styles.formGroup}>
            <div className={styles.inputWrapper}>
              <DateInput
                label="Entrada"
                name="checkIn"
                value={booking.checkIn}
                onChange={handleChange}
                message="Data de Entrada"
                required={false}
                disabled={false}
              />
            </div>

            <div className={styles.inputWrapper}>
              <DateInput
                label="Saída"
                name="checkOut"
                value={booking.checkOut}
                onChange={handleChange}
                message="Data de Saída"
                required={false}
                disabled={false}
              />
            </div>

            <button className={styles.searchButton} aria-label="Buscar disponibilidade">
              <Search size={24} strokeWidth={2.5} />
            </button>
          </div>
          
          <p className={styles.helperText}>
            Digite a data para consultar os quartos disponíveis!
          </p>
        </div>
      </section>
      
      <div className={styles.spacer}></div>
    </div>
  );
};

export default Home;