import random
import math
import matplotlib.pyplot as plt
import time
import numpy as np

n = 8
tics = 1024
max_amp = 1200

delta_amp = max_amp/n
freqs = []

for i in range(1, n + 1):
    freq = delta_amp * i
    freqs.append(freq) 


def generate_params(time):
    amps = []
    phases = []
    for i in range(n):
        phase = []
        amp = []
        for j in range(time):
            amp.append(random.random())
            phase.append(random.random())
        amps.append(amp)
        phases.append(phase)
    return {"amps": amps, "phases": phases}    


def generate_signal(time):
    res = generate_params(time)
    amps = res["amps"]
    phases = res["phases"]
    
    signal_sum = []
    for t in range(time):
        signals = []
        sum = 0
        for j in range(n):
            signal = (amps[j][t])* math.sin(freqs[j]*t+ phases[j][t])
            signals.append(signal)
        for s in signals:
            sum = sum + s
        
        signal_sum.append(sum)
    return signal_sum

signal = generate_signal(tics)

#functions for plots
def plot_f(x):
    plt.xlabel('частота')
    plt.title('Графік перетвореного сигналу')
    plt.plot(range(len(x)), x, '#E84A5F')
    plt.show()

def plot_N(x, times):
    plt.plot(x, times, color = '#51074a')
    plt.xlabel('час - кількість дискретних відліків') 
    plt.ylabel('час обчислення')
    plt.title('Графік залежності складності обчислень від часу') 
    plt.show()

def plot_multiple(x, times, times_numpy):
    fig, axs = plt.subplots(2)
    fig.suptitle('Графік залежності складності обчислень від часу')
    axs[0].plot(x, times, color = '#F67280')
    axs[1].plot(x, times_numpy, color = '#6C5B7B')
    plt.xlabel('час - кількість дискретних відліків')
    for ax in axs.flat:
        ax.set(ylabel='час обчислення')
    axs[0].set_title('dft')
    axs[1].set_title('numpy')
    plt.show()


#main part - function for calculation

def fourier_transform(x):
    N = len(x)
    f = []
    for p in range(N):
        res = 0
        for k in range(N):
            w_num = p*k*2*math.pi/N
            w = math.cos(w_num) - math.sin(w_num)*1j
            res += x[k] * w
        #f.append(abs(res))
        f.append(res)
    return f


#building time complexity, comparing with numpy dft

min_ticks = 100
max_tick = 5000

times = []
times_numpy = []
N = []

for tic in range(min_ticks, max_tick+1, 100):
    signal = generate_signal(tic)
    N.append(tic)

    start_time = time.perf_counter()
    fourier_transform(signal)
    end_time = time.perf_counter()
    times.append(end_time - start_time)

    start_time_numpy = time.perf_counter()
    np.fft.fft(signal)
    end_time_numpy = time.perf_counter()
    times_numpy.append(end_time_numpy - start_time_numpy)

plot_multiple(N, times, times_numpy)