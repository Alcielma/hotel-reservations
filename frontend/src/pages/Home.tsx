import { useState } from "react";
import DateInput from "../components/ui/Date";
import { Search } from "lucide-react";
import hotel  from "./../assets/images/hotel.png"

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
    <div className="w-full flex flex-col">
      {/* --- BANNER PRINCIPAL --- */}
      <section className="relative w-full h-125 overflow-hidden">
        <img
          src={hotel}
          alt="Horizon Hotel Exterior"
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-black/10"></div>
      </section>

      {/* --- SEÇÃO DE BUSCA (DISPONIBILIDADE) --- */}
      <section className="max-w-7xl mx-auto w-full px-12 py-8">
        <div className="flex flex-col gap-6">
          <h2 className="text-4xl font-bold text-gray-900 tracking-tight">
            Período da Acomodação
          </h2>

          <div className="flex flex-col md:flex-row items-end gap-4">
            <div className="w-64">
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

            <div className="w-64">
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

            <button className="bg-[#1e293b] text-white p-3.5 rounded-full hover:bg-slate-700 transition-all shadow-md active:scale-95 mb-0.5">
              <Search size={24} strokeWidth={2.5} />
            </button>
          </div>
          
          <p className="text-red-500 text-sm italic">
            Digite a data para consultar os quartos disponíveis!
          </p>
        </div>
      </section>
      
      <div className="h-10"></div>
    </div>
  );
};

export default Home;